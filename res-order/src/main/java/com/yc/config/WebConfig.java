package com.yc.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
//@LoadBalancerClient(name = "resfood",configuration = {OnlyOneLoadBalancerConfiguration.class})
@LoadBalancerClients(
        value = {
                @LoadBalancerClient(name = "resfood",configuration = {OnlyOneLoadBalancerConfiguration.class}),
                @LoadBalancerClient(name = "resorder",configuration = {OnlyOneLoadBalancerConfiguration.class})
        },defaultConfiguration = LoadBalancerClientConfiguration.class
)
public class WebConfig {
    @LoadBalanced //负载均衡 => resfood服务下有两个服务节点 一个请求会访问哪个节点?
    @Bean  //IOC restemplate 对象托管spring
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
