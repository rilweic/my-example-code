//package com.lichao666.topic;
//
//import kafka.admin.AdminUtils;
//import kafka.admin.RackAwareMode;
//import kafka.utils.ZkUtils;
//import org.apache.kafka.common.security.JaasUtils;
//
//import java.util.Properties;
//
//public class TopicAction {
//
//    public static void main(String[] args) {
//
//        ZkUtils zkUtils = ZkUtils.apply("node1:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
//// 创建一个单分区单副本名为t1的topic
//        AdminUtils.createTopic(zkUtils, "first", 3, 3, new Properties(), RackAwareMode.Enforced$.MODULE$);
//        zkUtils.close();
//    }
//}
