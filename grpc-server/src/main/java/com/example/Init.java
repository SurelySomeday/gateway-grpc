package com.example;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @Author yanxin.
 * @Date 2022/11/9 18:04.
 * Created by IntelliJ IDEA
 * File Description:
 */
@Component
public class Init implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        Properties properties = new Properties();
        properties.put("serverAddr", "yxlgx.top:8848");
        properties.put("namespace", "dev");
        NamingService namingService = NamingFactory.createNamingService(properties);
        namingService.registerInstance("grpc-server", "127.0.0.1", 6565);
    }
}
