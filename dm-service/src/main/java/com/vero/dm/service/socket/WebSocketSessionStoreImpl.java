package com.vero.dm.service.socket;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Optional;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:50 2018/8/12.
 * @since data-mining-platform
 */
@Slf4j
@Service
public class WebSocketSessionStoreImpl implements WebSocketSessionStore
{
    /**
     * Key (String) = ID of the client system Value (Deque<WebSocketSession>) = WebSocket
     * connections of the client system
     */
    @Getter
    private final ConcurrentHashMap<String, Deque<WebSocketSession>> lookupTable = new ConcurrentHashMap<>();

    @Override
    public synchronized void add(String systemID, WebSocketSession session)
    {
        Deque<WebSocketSession> sessionSet = lookupTable.get(systemID);
        if (sessionSet == null)
        {
            sessionSet = new ArrayDeque<>();
            sessionSet.add(session);
            lookupTable.put(systemID, sessionSet);

        }
        else
        {
            sessionSet.addLast(session); // Adding at the end
        }
        log.debug("A new WebSocketSession with id '{}' is stored for system '{}' (size: {})",
            session.getId(), systemID, sessionSet.size());
    }

    @Override
    public synchronized void remove(String systemID, WebSocketSession session)
    {
        Deque<WebSocketSession> sessionSet = lookupTable.get(systemID);
        if (sessionSet == null)
        {
            return;
        }

        // Prevent java.util.ConcurrentModificationException: null
        // Reason: Cannot modify the set (remove the item) we are iterating
        // Solution: Iterate the set, find the item, remove the item after the for-loop
        //
        WebSocketSession toRemove = null;
        for (WebSocketSession wss : sessionSet)
        {
            if (wss.getId().equals(session.getId()))
            {
                toRemove = wss;
                break;
            }
        }

        if (toRemove != null && sessionSet.remove(toRemove))
        {
            log.debug("The WebSocketSession with id '{}' is removed for system '{}' (size: {})",
                session.getId(), systemID, sessionSet.size());
        }
        else
        {
            log.error("Failed to remove the WebSocketSession with id '{}' for system '{}'",
                session.getId(), systemID);
        }
    }

    @Override
    public synchronized Optional<WebSocketSession> get(String systemID, String sessionID)
    {
        Deque<WebSocketSession> sessionSet = lookupTable.get(systemID);

        if (sessionSet != null)
        {
            for (WebSocketSession wss : sessionSet)
            {
                if (wss.getId().equals(sessionID))
                {
                    return Optional.of(wss);
                }
            }
        }

        return Optional.absent();
    }

    /**
     * sessionSet.removeFirst() will throw NoSuchElementException, if the sessionSet is empty. But
     * the sessionSet itself might be null, if there is no connection established and therefore,
     * the sessionSet is not initialized, yet. In this case we throw the same exception, since the
     * ProducerImpl decides on the context based on the exception type.
     */
    @Override
    public synchronized WebSocketSession getNext(String systemID)
    {
        Deque<WebSocketSession> sessionSet = lookupTable.get(systemID);
        if (sessionSet == null)
        {
            throw new NoSuchElementException();
        }
        // Get the first item, and add at the end
        WebSocketSession s = sessionSet.removeFirst();
        sessionSet.addLast(s);
        return s;
    }

    @Override
    public int size(String systemID)
    {
        Deque<WebSocketSession> sessionSet = lookupTable.get(systemID);
        if (sessionSet == null)
        {
            return 0;
        }
        else
        {
            return sessionSet.size();
        }
    }

    @Override
    public void clear()
    {
        lookupTable.clear();
        log.debug("Cleared the WebSocketSession store");
    }

    @Override
    public String toString()
    {
        return lookupTable.toString();
    }
}
