package com.lichao666.eurekconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 消费类，注意要加上 EnableEurekaClient 注解。表示当前服务是客户端消费
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaConsumerApplication {

    @Bean
//    @LoadBalanced
/**添加注解表示负载均衡，可直接通过RestTemplate对象调用服务提供方接口。
 和LoadBalancerClient不能同时使用，否则会冲突。测试ribbon时，如果要使用LoadBalancerClient 打印服务地址信息，需要将此处注释掉 */
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class, args);
    }

}
