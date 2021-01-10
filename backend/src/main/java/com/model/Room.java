package com.model;

import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Room {

    private UUID id;
    private String name;
    private List<Player> players;
    private Game game;

    public Room(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.players = new LinkedList<>();
        this.game = new Game();
    }

    public void addPlayer(Session playerId, String playerName) {
        players.add(new Player(playerId, playerName));
    }

    public void removePlayer(String sessionId) {
        players = players.stream().filter(player -> !player.getSession().getId().equals(sessionId)).collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
