package com.vero.dm.api.config;


import com.vero.dm.api.config.handlers.WebSocketEndpoint;
import com.vero.dm.api.interceptor.WebSocketSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:38 2018/8/12.
 * @since data-mining-platform
 */

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer
{
    private WebSocketEndpoint webSocketEndpoint;

    private WebSocketSessionInterceptor sessionInterceptor;

    @Autowired
    public void setWebSocketEndpoint(WebSocketEndpoint webSocketEndpoint)
    {
        this.webSocketEndpoint = webSocketEndpoint;
    }

    @Autowired
    public void setSessionInterceptor(WebSocketSessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry)
    {
        webSocketHandlerRegistry.addHandler(webSocketEndpoint, "/socket").setAllowedOrigins("*")
                .addInterceptors(sessionInterceptor);
    }
}
