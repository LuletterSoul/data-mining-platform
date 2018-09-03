package com.vero.dm.api.config.handlers;


import java.io.IOException;
import java.util.Deque;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.vero.dm.service.constant.SocketConstants;
import com.vero.dm.service.socket.WebSocketSessionStore;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:24 2018/8/12.
 * @since data-mining-platform
 */
@Component
@Slf4j
public class WebSocketEndpoint extends ConcurrentTextWebSocketHandler
{

    private static final Object LOCK = new Object();

    private WebSocketSessionStore webSocketSessionStore;

    @Autowired
    public void setWebSocketSessionStore(WebSocketSessionStore webSocketSessionStore)
    {
        this.webSocketSessionStore = webSocketSessionStore;
    }

    /**
     * Close the sessions for a graceful shutdown
     */
    @PreDestroy
    public void destroy()
    {
        Map<String, Deque<WebSocketSession>> sessionMap = webSocketSessionStore.getLookupTable();

        for (Deque<WebSocketSession> sessionsForOneSystem : sessionMap.values())
        {
            for (WebSocketSession session : sessionsForOneSystem)
            {
                closeSession(session);
            }
        }
    }

    @Override
    public void onMessage(WebSocketSession session, TextMessage webSocketMessage)
        throws Exception
    {
        log.info("[id={}] Received message: {}", session.getId(), webSocketMessage.getPayload());
        String payload = webSocketMessage.getPayload();
        // CommunicationContext context = new CommunicationContext(session, payload);
        // try
        // {
        // consumer.consume(context);
        // }
        // catch (IxsiProcessingException e)
        // {
        // handleError(session, payload, e);
        // }
    }

    @Override
    public void onOpen(WebSocketSession session)
        throws Exception
    {
        log.info("New connection established: {}", session);
        String systemId = (String)session.getAttributes().get(SocketConstants.SYSTEM_ID_KEY);
        webSocketSessionStore.add(systemId, session);
    }

    @Override
    public void onClose(WebSocketSession session, CloseStatus closeStatus)
        throws Exception
    {
        log.info("[id={}] Connection was closed, status: {}", session.getId(), closeStatus);
        String systemId = (String)session.getAttributes().get(SocketConstants.SYSTEM_ID_KEY);
        synchronized (LOCK)
        {
            webSocketSessionStore.remove(systemId, session);

            if (webSocketSessionStore.size(systemId) == 0)
            {
                unSubscribeStores(systemId);
            }
        }
    }

    @Override
    public void onError(WebSocketSession session, Throwable throwable)
        throws Exception
    {
        log.error("Oops", throwable);
        // TODO catch transportexceptions!
    }

    @Override
    public boolean supportsPartialMessages()
    {
        return false;
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * If there's something fundamentally wrong with the incoming message or its processing (like
     * receiving non-Ixsi strings or parsing the request) from which the system cannot recover and
     * send the appropriate IXSI error message, we cannot do anything but send a simple error
     * string for debugging purposes and close the session.
     */
//    private void handleError(WebSocketSession session, String payload, IxsiProcessingException e)
//        throws IOException
//    {
//        log.error("Error occurred", e);
//        String errorMsg = "IxsiProcessingException: " + e.getLocalizedMessage();
//
//        session.sendMessage(
//            new TextMessage(errorMsg + "\nMessage that caused the exception: " + payload));
//        session.close(CloseStatus.NOT_ACCEPTABLE.withReason(errorMsg));
//    }

    private void closeSession(WebSocketSession session)
    {
        if (session.isOpen())
        {
            try
            {
                session.close(new CloseStatus(1001, "BikeMan is shutting down"));
            }
            catch (IOException e)
            {
                log.error("Failed to close the session", e);
            }
        }
    }

    private void unSubscribeStores(String systemId)
    {
        log.debug("There are no open connections left to system '{}'. "
                  + "Removing it from all the subscription stores",
            systemId);
    }
}
