package com.resource;

import com.model.Room;
import com.service.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("room")
public class RoomResource {

    @Inject
    RoomService roomService;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Room newRoom(String name) {
        return roomService.newRoom(name);
    }

    @GET
    public List<Room> getRooms() {
        return roomService.getRooms();
    }

    @GET
    @Path("/{id}")
    public Room getRoom(@PathParam("id") String id) {
        return roomService.getRoom(UUID.fromString(id));
    }
}
