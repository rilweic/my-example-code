package com.lichao666.procuder;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class NewProducer2 {
    public static void main(String[] args) {
        Properties props = new Properties();
        // Kafka 服务端的主机名和端口号
        props.put("bootstrap.servers", "node1:9092,node2:9092,node3:9092");
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
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 500; i++) {
            producer.send(new ProducerRecord<String, String>("first", Integer.toString(i), "job_info -" + i));
//            try {
//                TimeUnit.SECONDS.sleep(1);
                System.out.println("发送："+i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        producer.flush();
        producer.close();
    }
}
