package com.wj.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 负载均衡
 *
 * @author wangjie
 */
@Component
public class LoadBalancedClientFilter extends LoadBalancerClientFilter {

    @Autowired
    private DiscoveryClient discoveryClient;

    public LoadBalancedClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
        String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
        String servHeaderIp = exchange.getRequest().getHeaders().getFirst("server-ip");
        String servHeaderName = exchange.getRequest().getHeaders().getFirst("server-name");
        List<ServiceInstance> instances = null;
        //先在header的查询匹配IP
        if (servHeaderIp != null) {
            if (servHeaderName != null && servHeaderName.equals(serviceId)) {
                instances = discoveryClient.getInstances(servHeaderName);
            } else {
                instances = discoveryClient.getInstances(serviceId);
            }
            // 服务IP和HOST灰度匹配路由
            if (instances != null && instances.size() > 0) {
                Optional<ServiceInstance> custInstances = instances.stream()
                        .filter(server -> server.getHost().equals(servHeaderIp))
                        .findFirst();
                // 找到对应iP的服务
                return custInstances.isPresent() ? custInstances.get() : super.choose(exchange);
            }
            //再在nacos meatdata的查询匹配IP
        } else {
            instances = discoveryClient.getInstances(serviceId);
            if (instances.size() != 0) {
                String servNacosIp = null;
                String servNacosName = null;
                for (ServiceInstance serviceInstance : instances) {
                    if (serviceInstance.getMetadata().get("serv-ip") != null) {
                        servNacosIp = serviceInstance.getMetadata().get("serv-ip");
                    }
                    if (serviceInstance.getMetadata().get("serv-name") != null) {
                        servNacosName = serviceInstance.getMetadata().get("serv-name");
                    }
                }
                if (servNacosIp != null) {

                    if (servNacosName != null && servNacosName.equals(serviceId)) {
                        instances = discoveryClient.getInstances(servNacosName);
                    }
                    String finalServNacosIp = servNacosIp;
                    Optional<ServiceInstance> custInstances = instances.stream()
                            .filter(server -> server.getHost().equals(finalServNacosIp))
                            .findFirst();
                    // 找到对应iP的服务
                    return custInstances.isPresent() ? custInstances.get() : super.choose(exchange);
                }
            }
        }
        return super.choose(exchange);
    }

}

