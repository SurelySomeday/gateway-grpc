package top.yxlgx;

import top.yxlgx.grpcserver.hello.HelloRequest;
import top.yxlgx.grpcserver.hello.HelloResponse;
import top.yxlgx.grpcserver.hello.HelloServiceGrpc;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import static io.grpc.netty.shaded.io.grpc.netty.NegotiationType.TLS;

@SpringBootApplication
public class GrpcClientApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws SSLException {

        int gatewayPort = 8090;
        ManagedChannel originChannel = createSecuredChannel(gatewayPort);
        ClientInterceptor interceptor = new HeaderClientInterceptor();
        // @1 构建Channel时注入客户端拦截器
        Channel channel = ClientInterceptors.intercept(originChannel, interceptor);
        final HelloResponse response = HelloServiceGrpc.newBlockingStub(channel)
                .hello(HelloRequest.newBuilder().setFirstName("Saul")
                        .setLastName("Hudson").build());

        System.out.println(response.toString());
        final HelloResponse response2 = HelloServiceGrpc.newBlockingStub(channel)
                .hello(HelloRequest.newBuilder().setFirstName("aa")
                        .setLastName("Hudaason").build());
        System.out.println(response2.toString());
        originChannel.shutdown();
    }

    private ManagedChannel createSecuredChannel(int port) throws SSLException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    public void checkClientTrusted(X509Certificate[] certs,
                                                   String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs,
                                                   String authType) {
                    }
                }};

        return NettyChannelBuilder.forTarget("localhost:"+port+"/test/")
                .useTransportSecurity().sslContext(
                        GrpcSslContexts.forClient().trustManager(trustAllCerts[0])
                                .build()).negotiationType(TLS).build();
    }
}
