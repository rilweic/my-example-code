package com.lichao666.consumer;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SslConfigs;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class CustomerSSL {
    public static final String topic="lichao_test";
    //    public static final String bootstrap_server="node1:9092,node2:9092,node3:9092";
//    public static final String bootstrap_server="node1:19092,node2:19092,node3:19092";
    public static final String bootstrap_server="114.251.155.29:9096,114.251.155.29:9097,114.251.155.29:9095";
    //    public static final String client_truststore="/Users/lichao/useless/kafka-ssl/kafka.truststore";
//    public static final String client_keystore="/Users/lichao/useless/kafka-ssl/kafka.keystore";
//    public static final String client_ssl_password="Qaz!2345";
    public static final String client_truststore="/Users/lichao/useless/kafka-old-ssl/client.truststore.jks";
    public static final String client_keystore="/Users/lichao/useless/kafka-old-ssl/server.keystore.jks";
    public static final String client_ssl_password="idsc2020";
    public static final String consumer_group="group2_topic1";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);

        //configure the following three settings for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");

        props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG,"idsc2020");
        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG,"idsc2020");


//        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, client_truststore);
//        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  client_ssl_password);

        //configure the following three settings for SSL Authentication
//        props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, client_keystore);
//        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, client_ssl_password);
//        props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, client_ssl_password);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer_group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(props);
        CustomerSSL.TestConsumerRebalanceListener2 rebalanceListener = new CustomerSSL.TestConsumerRebalanceListener2();
        consumer.subscribe(Collections.singletonList(topic), rebalanceListener);
        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(1000);
            for (ConsumerRecord<byte[], byte[]> record : records) {
                System.out.printf("Received Message topic =%s, partition =%s, offset = %d, key = %s, value = %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
            }
            consumer.commitSync();
        }
    }
    private static class  TestConsumerRebalanceListener2 implements ConsumerRebalanceListener {
        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            System.out.println("Called onPartitionsRevoked with partitions:" + partitions);
        }
        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            System.out.println("Called onPartitionsAssigned with partitions:" + partitions);
        }
    }
}
