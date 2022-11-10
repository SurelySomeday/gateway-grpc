package top.yxlgx;

import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author yanxin.
 * @Date 2022/11/10 9:47.
 * Created by IntelliJ IDEA
 * File Description:
 */
@Component
public class GrpcNacosRegistration implements CommandLineRunner {

    @Value("${grpc.server.port:6565}")
    Integer port;
    @Resource
    NacosRegistration nacosRegistration;
    @Resource
    NacosServiceRegistry nacosServiceRegistry;

    @Override
    public void run(String... args) {
        nacosRegistration.setPort(port);
        nacosServiceRegistry.register(nacosRegistration);
    }
}
