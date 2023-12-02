package com.yc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OnlyOneLoadBalancerConfiguration {

//        //配置自定义策略
//        @Bean
//        public ReactorServiceInstanceLoadBalancer OnlyOneReactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
//            String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//            //随机RandomLoadBalancer；轮询RoundRobinLoadBalancer
//            return new OnlyOneLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
//        }


        //配置成轮询
    @Bean
    public ReactorServiceInstanceLoadBalancer roundRobinReactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        //随机RandomLoadBalancer；轮询RoundRobinLoadBalancer
        return new RoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}
