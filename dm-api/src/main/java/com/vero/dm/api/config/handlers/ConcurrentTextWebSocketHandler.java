package com.vero.dm.api.config.handlers;


import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.vero.dm.service.constant.SocketConstants;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:05 2018/8/12.
 * @since data-mining-platform
 */

public abstract class ConcurrentTextWebSocketHandler extends TextWebSocketHandler
{

    private static final int sendTimeLimit = (int)TimeUnit.SECONDS.toMillis(10);

    private static final int bufferSizeLimit = 5 * SocketConstants.MAX_TEXT_MSG_SIZE;

    private final Map<String, ConcurrentWebSocketSessionDecorator> sessions = new ConcurrentHashMap<>();

    // -------------------------------------------------------------------------
    // Delegate methods
    // -------------------------------------------------------------------------

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
        throws Exception
    {
        ConcurrentWebSocketSessionDecorator decorator = new ConcurrentWebSocketSessionDecorator(
            session, sendTimeLimit, bufferSizeLimit);

        sessions.put(session.getId(), decorator);
        this.onOpen(decorator);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
        throws Exception
    {
        ConcurrentWebSocketSessionDecorator decorator = sessions.remove(session.getId());
        this.onClose(decorator, closeStatus);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage webSocketMessage)
        throws Exception
    {
        this.onMessage(internalGet(session), webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable)
        throws Exception
    {
        this.onError(internalGet(session), throwable);
    }

    private ConcurrentWebSocketSessionDecorator internalGet(WebSocketSession session)
    {
        ConcurrentWebSocketSessionDecorator decorator = sessions.get(session.getId());
        if (decorator == null)
        {
            throw new NoSuchElementException();
        }
        return decorator;
    }

    // -------------------------------------------------------------------------
    // Implement in extending classes
    // -------------------------------------------------------------------------

    abstract void onMessage(WebSocketSession session, TextMessage webSocketMessage)
        throws Exception;

    abstract void onOpen(WebSocketSession session)
        throws Exception;

    abstract void onClose(WebSocketSession session, CloseStatus closeStatus)
        throws Exception;

    abstract void onError(WebSocketSession session, Throwable throwable)
        throws Exception;
}
