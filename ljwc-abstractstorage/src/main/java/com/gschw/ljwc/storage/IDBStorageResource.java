package com.gschw.ljwc.storage;

import org.joda.time.DateTime;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;


import java.util.List;

/**
 * Created by hadoop on 7/9/15.
 */
public interface IDBStorageResource {







    //void write(DBStorageElement element);
    //void write(List<DBStorageElement> elements);
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response write(@NotNull DBStorageElementsCollection elementsCollection);

    //void clear();
    @DELETE
    //@Path("/")
    Response clear();

    //boolean remove(String key);
    @Path("{key}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("key") @NotBlank String key);

    //boolean remove(String key, Datetime timestamp);
    @Path("{key}/{timestamp}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp);

    //List<DBStorageElement> read();
    //@Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response read();

    //List<DBStorageElement> read(String key);
    @Path("{key}/data")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response read(@PathParam("key") @NotBlank String key);

    //DBStorageElement read(String key, DateTime timestamp);
    @Path("{key}/{timestamp}/data")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response readElement(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp);

    //DBStorageElement readLast(String key);
    @Path("{key}/lastdata")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response readLast(@PathParam("key") @NotBlank String key);

    //boolean exists(String key);
    @Path("{key}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response existsAny(@PathParam("key") @NotBlank String key);

    //boolean exists(String key, DateTime timestamp);
    @Path("{key}/{timestamp}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response exists(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp);
}
