package com.socket;

import com.service.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint("/socket/main/{roomId}/{username}")
public class MainSocket {

    @Inject
    RoomService roomService;

    private static final Logger LOGGER = Logger.getLogger("MainSocket");

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("roomId") String roomId) throws IOException {
        if (roomService.addPlayerToRoom(UUID.fromString(roomId), session, username)) {
            session.getUserProperties().put("username", username);
            session.getUserProperties().put("roomId", roomId);

            sessions.put(session.getId(), session);

            broadcast("User " + username + " joined");
            LOGGER.info("session " + session.getId() + " connected to " + this.getClass().getSimpleName());
        } else session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"room does not exist"));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session.getId());
        roomService.removePlayerFromRoom(UUID.fromString((String)session.getUserProperties().get("roomId")), session);

        broadcast("User " + session.getUserProperties().get("username") + " left");
        LOGGER.info("session " + session.getId() + " disconnected from " + this.getClass().getSimpleName());
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        sessions.remove(session.getId());
        roomService.removePlayerFromRoom(UUID.fromString((String)session.getUserProperties().get("roomId")), session);

        broadcast("User " + session.getUserProperties().get("username") + " left on error: " + throwable);
        LOGGER.throwing(this.getClass().getSimpleName(), "onError", throwable);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        broadcast(session.getUserProperties().get("username") +  ":" + message);

        LOGGER.info("session " + session.getId() + " sent message \"" + message + "\"");
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
