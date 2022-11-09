package com.example;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author yanxin.
 * @Date 2022/11/9 16:22.
 * Created by IntelliJ IDEA
 * File Description:
 */
public class HeaderClientInterceptor implements ClientInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HeaderClientInterceptor.class.getName());

    @VisibleForTesting
    static final Metadata.Key<String> CUSTOM_HEADER_KEY =
            Metadata.Key.of("custom_client_header_key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // @1 在Header中设置需要透传的值
                headers.put(CUSTOM_HEADER_KEY, "grpc-path");
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        // @2 获取从服务端返回的Header信息
                        logger.info("header received from server:" + headers);
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
