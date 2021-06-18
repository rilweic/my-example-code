package com.lichao666.eurekconsumer.service.impl;

import com.lichao666.eurekconsumer.entity.Family;
import com.lichao666.eurekconsumer.entity.User;
import com.lichao666.eurekconsumer.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Family getFamilyById(Integer id) {


        return new Family("李超", queryRemoteUsers());
    }

    @Override
    public Family getFamilyByLoadBalance() {

        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-provider");
        StringBuilder sb = new StringBuilder();
        // 拼接服务地址
        sb.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("/list");
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        List<User> users = responseEntity.getBody();
        return new Family("李超2with loadbalance", users);
    }

    @Override
    public Family getByLoadBalanceAnnotation() {
        ResponseEntity<List<User>> exchange = restTemplate.exchange(
                // 直接调用 application name
                "http://eureka-provider/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        );
        return new Family("李超with loadbalance annotation",exchange.getBody());
    }

    private List<User> queryRemoteUsers() {
        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            System.out.println("服务列表为空");
            return null;
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        if (CollectionUtils.isEmpty(instances)) {
            System.out.println("实例数为0");
            return null;
        }
        ServiceInstance serviceInstance = instances.get(0);
        StringBuilder sb = new StringBuilder();
        // 拼接远程服务地址
        sb.append("http://").append(serviceInstance.getHost()).append(":").append(serviceInstance.getPort()).append("/list");
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(sb.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });

        return responseEntity.getBody();

    }
}
