package com.vero.dm.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import com.vero.dm.api.config.handlers.WebSocketEndpoint;
import com.vero.dm.api.interceptor.WebSocketSessionInterceptor;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:27 2018/8/12.
 * @since data-mining-platform
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer
{
    private WebSocketEndpoint webSocketEndpoint;

    private WebSocketSessionInterceptor sessionInterceptor;

    @Autowired
    public void setWebSocketEndpoint(WebSocketEndpoint webSocketEndpoint)
    {
        this.webSocketEndpoint = webSocketEndpoint;
    }

    @Autowired
    public void setSessionInterceptor(WebSocketSessionInterceptor sessionInterceptor)
    {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry)
    {
        // 添加服务端点，可以理解为某一服务的唯一key值
        // 当浏览器支持sockjs时执行该配置
        stompEndpointRegistry.addEndpoint("/socket").setAllowedOrigins("*").addInterceptors(
            sessionInterceptor).withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration)
    {
        registration.addDecoratorFactory(handler -> new WebSocketHandlerDecorator(webSocketEndpoint));
    }

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry registry)
    // {
    // // 配置接受订阅消息地址前缀为topic的消息
    // registry.enableSimpleBroker("/download_progress");
    // }
}
