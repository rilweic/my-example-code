package com.game.cubev2;

import javax.sound.sampled.*;

public class SoundUtils {

    /**
     * 播放自定义生成的游戏音效
     * @param startFreq 起始频率(Hz)
     * @param endFreq   结束频率(Hz)
     * @param duration  持续时间(秒)
     * @param amplitude 振幅(0.0-1.0)
     */
    public static void playSound(double startFreq, double endFreq,
                                 double duration, double amplitude) {
        try {
            // 音频格式参数
            int sampleRate = 44100;
            int bitDepth = 16;
            AudioFormat format = new AudioFormat(sampleRate, bitDepth, 1, true, false);

            // 获取音频线路
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();

            // 生成音频数据
            byte[] audioData = generateGameSound(startFreq, endFreq, duration,
                    amplitude, sampleRate, bitDepth);

            // 播放音频
            line.write(audioData, 0, audioData.length);

            // 释放资源
            line.drain();
            line.close();
        } catch (LineUnavailableException e) {
            System.err.println("音频设备不可用: " + e.getMessage());
        }
    }

    private static byte[] generateGameSound(double startFreq, double endFreq,
                                            double duration, double amplitude,
                                            int sampleRate, int bitDepth) {
        int numSamples = (int)(duration * sampleRate);
        byte[] data = new byte[numSamples * 2];

        // 音效参数默认值
        double attack = 0.1;
        double decay = 0.4;
        double sustain = 0.6;
        double cutoffFreq = 4000;
        double waveMixRatio = 0.7; // 方波混合比例

        double filterState = 0.0;

        for(int i = 0; i < numSamples; i++) {
            double t = i / (double)sampleRate;
            double progress = i / (double)numSamples;

            // 动态频率
            double freq = startFreq * Math.pow(endFreq/startFreq, progress);

            // 波形合成
            double square = Math.signum(Math.sin(2 * Math.PI * freq * t));
            double sine = Math.sin(2 * Math.PI * freq * t);
            double wave = (waveMixRatio * square) + ((1 - waveMixRatio) * sine);

            // 包络控制
            double envelope = calculateEnvelope(progress, attack, decay, sustain);

            // 滤波处理
            double filtered = lowPassFilter(wave * envelope * amplitude,
                    filterState, cutoffFreq, sampleRate);
            filterState = filtered;

            // 转换为16位PCM
            short sample = (short)(filtered * Short.MAX_VALUE);
            data[2*i] = (byte)(sample & 0xFF);
            data[2*i + 1] = (byte)((sample >> 8) & 0xFF);
        }
        return data;
    }

    private static double calculateEnvelope(double progress,
                                            double attack,
                                            double decay,
                                            double sustain) {
        if(progress < attack) {
            return smoothStep(0.0, 1.0, progress/attack);
        } else if(progress < attack + decay) {
            return 1.0 - (1.0 - sustain) * ((progress - attack)/decay);
        } else {
            return sustain * (1.0 - (progress - attack - decay)/(1 - attack - decay));
        }
    }

    private static double lowPassFilter(double input, double prevOutput,
                                        double cutoffFreq, double sampleRate) {
        double RC = 1.0 / (2 * Math.PI * cutoffFreq);
        double alpha = 1.0 / (1.0 + RC * sampleRate);
        return alpha * input + (1 - alpha) * prevOutput;
    }

    private static double smoothStep(double edge0, double edge1, double x) {
        x = Math.max(0.0, Math.min((x - edge0)/(edge1 - edge0), 1.0));
        return x*x*(3 - 2*x);
    }

    public static void defaultPlay(){
        new Thread(() -> {
            try {
                playSound(1568, 1318, 0.6, 0.3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    // 示例调用
    public static void main(String[] args) {
        // 马里奥金币音效
        playSound(1568, 1318, 0.6, 0.3);

        // 自定义音效示例
        // playSound(1000, 800, 1.0, 0.5);
    }
}