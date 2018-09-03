package com.vero.dm.api.interceptor;


import java.net.InetSocketAddress;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:43 2018/8/12.
 * @since data-mining-platform
 */

@Component
@Slf4j
public class WebSocketSessionInterceptor extends HttpSessionHandshakeInterceptor
{
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes)
        throws Exception
    {
        InetSocketAddress inetSocketAddress = request.getRemoteAddress();
        log.info("Starting handshake with {}", inetSocketAddress);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex)
    {
        super.afterHandshake(request, response, wsHandler, ex);
        log.info("Handshake complete with {}", request.getRemoteAddress());
    }
}
