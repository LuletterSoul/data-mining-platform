package com.vero.dm.service.socket;


import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Optional;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:49 2018/8/12.
 * @since data-mining-platform
 */

public interface WebSocketSessionStore
{
    void add(String systemID, WebSocketSession session);

    void remove(String systemID, WebSocketSession session);

    Optional<WebSocketSession> get(String systemID, String sessionID);

    WebSocketSession getNext(String systemID);

    int size(String systemID);

    void clear();

    ConcurrentHashMap<String, Deque<WebSocketSession>> getLookupTable();
}
