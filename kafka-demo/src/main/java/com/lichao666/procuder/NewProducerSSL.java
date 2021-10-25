package com.lichao666.procuder;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SslConfigs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created By lujisen
 * @company ChinaUnicom Software JiNan
 * @date: 2020-02-23 08:58
 * @version: v1.0
 * @description: com.hadoop.ljs.kafka010
 */
public class NewProducerSSL {
    public static final String topic="test_topic";
    public static final String bootstrap_server="114.251.155.29:9095,114.251.155.29:9096,114.251.155.29:9097";
//    public static final String bootstrap_server="node1:19092,node2:19092,node3:19092";
//    public static final String bootstrap_server="node1:9092,node2:9092,node3:9092";
    public static final String client_truststore="/Users/lichao/useless/kafka-new-ssl/server.keystore.jks";
    public static final String client_keystore="/Users/lichao/useless/kafka-new-ssl/server.truststore.jks";
//    public static final String client_truststore="/Users/lichao/useless/kafka-old-ssl/client.truststore.jks";
//    public static final String client_keystore="/Users/lichao/useless/kafka-old-ssl/server.keystore.jks";
    public static final String client_ssl_password="idsc2020";
//    public static final String client_ssl_password="Qaz!2345";

    public static void main(String[] args){

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);
        props.put(ProducerConfig.RETRIES_CONFIG,3);
        props.put("max.in.flight.requests.per.connection",1);
        //configure the following three settings for SSL Encryption
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, client_truststore);
        props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  client_ssl_password);

        // configure the following three settings for SSL Authentication
        props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, client_keystore);
        props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, client_ssl_password);
        props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, client_ssl_password);

        props.put(ProducerConfig.ACKS_CONFIG, "all");
//        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put("enable.auto.commit",true);
        props.put("partitioner.class", "com.lichao666.procuder.partition.CustomPartitioner");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        TestCallback callback = new TestCallback();
        Random rnd = new Random();
        for (long i = 0; i <= 1; i++) {
            String  key=null;
            String value="{\"VEHICLE_ID\":\"京Qqqqq1\",\"OWNER_NAME\":\"曹操\",\"OPERATE\":\"[UPDATE]\",\"EXPIRE_TIME\":\"2031-08-09 00:00:00\",\"EFFECTIVE_TIME\":\"2021-08-09 14:58:11\",\"USER_ID\":\"\",\"CELL_ID\":\"6746dcd07cc54a94b244e015f13d986b\",\"DEVICE_ID\":[],\"REMARK\":\"\"}";

            System.out.println("Send Message: "+"Key:"+key+"Value:"+value);
            ProducerRecord<String, String> data = new ProducerRecord<String, String>(
                    topic, key, value);
            producer.send(data, callback);
//            producer.send(data);

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("发送："+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
//        producer.flush();
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