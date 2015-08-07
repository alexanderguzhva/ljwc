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
    //POST /
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response write(@NotNull DBStorageElementsCollection elementsCollection);

    //void clear();
    //DELETE /
    @DELETE
    //@Path("/")
    Response clear();

    //boolean remove(String key);
    //DELETE /{key}
    //
    //boolean remove(String key, Datetime timestamp);
    //DELETE /{key}?timestamp=x
    //
    @Path("{key}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp);

    //List<DBStorageElement> read();
    //GET /
    //@Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response read();

    //List<DBStorageElement> read(String key);
    //GET /{key}/element
    //
    //DBStorageElement read(String key, DateTime timestamp);
    //GET /{key}/element?timestamp=x
    @Path("{key}/element")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response readElement(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp);

    //DBStorageElement readLast(String key);
    //GET /{key}/lastelement
    @Path("{key}/lastelement")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response readLast(@PathParam("key") @NotBlank String key);

    //boolean exists(String key);
    //GET /{key}
    //
    //boolean exists(String key, DateTime timestamp);
    //GET /{key}?timestamp=x
    @Path("{key}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response exists(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp);
}
