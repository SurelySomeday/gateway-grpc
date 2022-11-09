package com.example;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;

/**
 * @Author yanxin.
 * @Date 2022/11/9 19:21.
 * Created by IntelliJ IDEA
 * File Description:
 */
@Component
public class FrontFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(GATEWAY_SCHEME_PREFIX_ATTR,"https");
        return chain.filter(exchange);
    }
}
