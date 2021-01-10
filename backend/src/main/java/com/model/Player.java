package com.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.websocket.Session;

public class Player {

    @JsonbTransient
    private Session session;
    private String name;

    public Player(Session session, String name) {
        this.session = session;
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
