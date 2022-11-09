package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;

@SpringBootApplication
public class SpringCloudGatewayGrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayGrpcApplication.class, args);
    }

}
