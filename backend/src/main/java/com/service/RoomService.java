package com.service;

import com.model.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoomService {

    Map<UUID, Room> rooms = new ConcurrentHashMap<>();

    public Room newRoom(String name) {
        UUID id = UUID.randomUUID();
        Room room = new Room(id, name);
        rooms.put(id, room);

        return room;
    }

    public void removeRoom(UUID roomId) {
        Room room = rooms.get(roomId);

        if (room != null) {

            if (room.getPlayers().size() > 0) {
                room.getPlayers().stream().forEach(player -> {
                    try {
                        player.getSession().close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"room was removed"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            rooms.remove(roomId);
        }
    }

    public boolean addPlayerToRoom(UUID roomId, Session session, String name) {
        Room room = rooms.get(roomId);

        if (room != null) {
            room.addPlayer(session, name);
            return true;
        }

        return false;
    }

    public void removePlayerFromRoom(UUID roomId, Session session) throws IOException {
        Room room = rooms.get(roomId);

        if (room != null) {
            room.removePlayer(session.getId());
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"player was removed from room"));

            if (room.getPlayers().size() == 0) {
                removeRoom(roomId);
            }
        }
    }

    public List<Room> getRooms() {
        return rooms.values().stream().collect(Collectors.toList());
    }

    public Room getRoom(UUID roomId) {
        return rooms.get(roomId);
    }
}
