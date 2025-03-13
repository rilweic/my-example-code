package com.lichao666;

import javax.sound.sampled.*;

public class JavaSoundTest {

    public static void main(String[] args) {
        int sampleRate = 44100;
        int bitDepth = 16;
        double duration = 0.5;  // 更符合游戏音效的短促时长

        try {
            AudioFormat format = new AudioFormat(sampleRate, bitDepth, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();

            byte[] audioData = generateSmoothCoinSound(duration, sampleRate, bitDepth);
            line.write(audioData, 0, audioData.length);

            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static byte[] generateSmoothCoinSound(double duration, int sampleRate, int bitDepth) {
        int numSamples = (int)(duration * sampleRate);
        byte[] data = new byte[numSamples * 2]; // 16-bit = 2 bytes/sample
        double amp = 0.3; // 降低整体振幅

        double startFreq = 1568; // G6
        double endFreq = 1318;  // E6

        // 改进的包络参数
        double attack = 0.1;
        double decay = 0.4;
        double sustainLevel = 0.6;

        // 低通滤波器参数
        double cutoffFreq = 4000; // 4kHz低通
        double filterState = 0.0;

        for(int i = 0; i < numSamples; i++) {
            double t = i / (double)sampleRate;
            double progress = i / (double)numSamples;

            // 指数频率滑音（更自然）
            double freq = startFreq * Math.pow(endFreq/startFreq, progress);

            // 混合波形（方波+正弦波）
            double squareWave = Math.signum(Math.sin(2 * Math.PI * freq * t));
            double sineWave = Math.sin(2 * Math.PI * freq * t);
            double wave = (0.7 * squareWave) + (0.3 * sineWave);

            // 改进的ADSR包络
            double envelope;
            if(progress < attack) {
                envelope = smoothStep(0.0, 1.0, progress/attack); // 平滑Attack
            } else if(progress < attack + decay) {
                envelope = 1.0 - (1.0 - sustainLevel) * ((progress - attack)/decay);
            } else {
                envelope = sustainLevel * (1.0 - (progress - attack - decay)/(1 - attack - decay));
            }

            // 应用低通滤波
            double filtered = lowPassFilter(wave * envelope * amp, filterState, cutoffFreq, sampleRate);
            filterState = filtered;

            short sample = (short)(filtered * Short.MAX_VALUE);

            data[2*i] = (byte)(sample & 0xFF);
            data[2*i + 1] = (byte)((sample >> 8) & 0xFF);
        }
        return data;
    }

    // 一阶低通滤波器实现
    private static double lowPassFilter(double input, double prevOutput, double cutoffFreq, double sampleRate) {
        double RC = 1.0 / (2 * Math.PI * cutoffFreq);
        double alpha = 1.0 / (1.0 + RC * sampleRate);
        return alpha * input + (1 - alpha) * prevOutput;
    }

    // 平滑过渡函数
    private static double smoothStep(double edge0, double edge1, double x) {
        x = Math.max(0.0, Math.min((x - edge0)/(edge1 - edge0), 1.0));
        return x*x*(3 - 2*x);
    }

    private static byte[] generateCoinSound(double duration, int sampleRate, int bitDepth) {
        int numSamples = (int)(duration * sampleRate);
        byte[] data = new byte[numSamples * (bitDepth/8)];
        double amp = 0.5;

        // 频率参数（单位：Hz）
        double startFreq = 1568; // G6
        double endFreq = 1318;   // E6

        // 包络参数（0.0-1.0）
        double attack = 0.05;  // 起始时间比例
        double decay = 0.3;   // 衰减时间比例

        for(int i = 0; i < numSamples; i++) {
            double t = i / (double)sampleRate;
            double progress = i / (double)numSamples;

            // 线性频率滑音
            double freq = startFreq + (endFreq - startFreq) * progress;

            // 方波合成（更接近8-bit音效）
            double wave = Math.signum(Math.sin(2 * Math.PI * freq * t));

            // ADSR包络
            double envelope;
            if(progress < attack) {
                envelope = progress / attack;  // Attack阶段
            } else if(progress < attack + decay) {
                envelope = 1.0 - 0.5*((progress - attack)/decay); // Decay阶段
            } else {
                envelope = 0.5 - 0.5*((progress - attack - decay)/(1 - attack - decay)); // Release
            }

            short sample = (short)(amp * envelope * wave * Short.MAX_VALUE);

            // 小端字节序写入
            data[2*i] = (byte)(sample & 0xFF);
            data[2*i + 1] = (byte)((sample >> 8) & 0xFF);
        }
        return data;
    }
}