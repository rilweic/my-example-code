package com.lichao666.flink.stream;

import com.lichao666.flink.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2021/1/31 10:14 PM
 * 复杂场景，除了获取最大温度的整个传感器信息以外，还要求时间戳更新成最新的
 */
public class TransformTest3_Reduce {
    public static void main(String[] args) throws Exception {
        // 创建 执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 执行环境并行度设置1
        env.setParallelism(1);

        DataStream<String> dataStream = env.readTextFile("datas/flink/sensor.txt");

        DataStream<SensorReading> sensorStream = dataStream.map(line -> {
            String[] fields = line.split(",");
            return new SensorReading(fields[0], new Long(fields[1]), new Double(fields[2]));
        });
        // 先分组再聚合
        // 分组
        KeyedStream<SensorReading, String> keyedStream = sensorStream.keyBy(SensorReading::getId);

        // reduce，自定义规约函数，获取max温度的传感器信息以外，时间戳要求更新成最新的
        DataStream<SensorReading> resultStream = keyedStream.reduce(
                (curSensor,newSensor)->new SensorReading(curSensor.getId(),newSensor.getTimestamp(), Math.max(curSensor.getTemperature(), newSensor.getTemperature()))
        );

        resultStream.print("result");

        env.execute();
    }
}