package com.lichao666.procuder;

import org.apache.kafka.clients.producer.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class NewProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        // Kafka 服务端的主机名和端口号
//        props.put("bootstrap.servers", "114.251.155.29:9092,114.251.155.29:9093,114.251.155.29:9094,114.251.155.29:9095,114.251.155.29:9096,114.251.155.29:9097");
//        props.put("bootstrap.servers", "114.251.155.29:9095,114.251.155.29:9096,114.251.155.29:9097");
        props.put("bootstrap.servers", "114.251.155.29:9092,114.251.155.29:9093,114.251.155.29:9094");
//        props.put("bootstrap.servers", "node1:9092,node2:9092,node3:9092");
//        props.put("bootstrap.servers", "cdhhadoop:30000");
        // 等待所有副本节点的应答
        props.put("acks", "all");
        // 消息发送最大尝试次数
        props.put("retries", 0);
        // 一批消息处理大小
        props.put("batch.size",  16384);
        // 请求延时
        props.put("linger.ms", 1);
        // 发送缓存区内存大小
        props.put("buffer.memory", 33554432);
        // key 序列化
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer"); // value 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.lichao666.procuder.partition.CustomPartitioner");
       TestCallback callback = new TestCallback();
        String value="{\"VEHICLE_ID\":\"京Qqqqq1\",\"OWNER_NAME\":\"曹操\",\"OPERATE\":\"[UPDATE]\",\"EXPIRE_TIME\":\"2031-08-09 00:00:00\",\"EFFECTIVE_TIME\":\"2021-08-09 14:58:11\",\"USER_ID\":\"\",\"CELL_ID\":\"6746dcd07cc54a94b244e015f13d986b\",\"DEVICE_ID\":[],\"REMARK\":\"\"}";
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 2; i++) {
            producer.send(new ProducerRecord<String, String>("test_topic", Integer.toString(i), value),callback);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("发送："+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.flush();
        producer.close();
    }

    private static class TestCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                System.out.println("offset:"+recordMetadata.offset());
                System.out.println("partiton:"+recordMetadata.partition());
                System.out.println("Error while producing message to topic :" + recordMetadata);
                e.printStackTrace();
            } else {
                System.out.println("offset:"+recordMetadata.offset());
                System.out.println("partiton:"+recordMetadata.partition());
                String message = String.format("sent message to topic:%s partition:%s  offset:%s", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                System.out.println(formatter.format(date) + "\t" + message);
            }
        }
    }
}
