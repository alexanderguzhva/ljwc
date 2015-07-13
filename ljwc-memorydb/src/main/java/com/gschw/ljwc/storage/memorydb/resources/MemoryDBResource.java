package com.gschw.ljwc.storage.memorydb.resources;

import com.gschw.ljwc.storage.*;
import com.gschw.ljwc.storage.memorydb.core.MemoryDB;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by nop on 6/24/15.
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MemoryDBResource implements IDBStorageResource {
    private MemoryDB memoryDB;

    public MemoryDBResource(MemoryDB memoryDB) {
        this.memoryDB = memoryDB;
    }


    //
    private DateTime parseDTFromString(String timestamp) {
        DateTime dt;

        ////
        long millis;
        try {
            millis = Long.parseLong(timestamp);

            //// ok.
            dt = new DateTime(millis);
        }
        catch (NumberFormatException e)
        {
            //// ok, try different parse
            dt = DateTime.parse(timestamp);
        }

        return dt;
    }


    @Override
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response write(DBStorageElementsCollection elementsCollection) {
        if (elementsCollection == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (memoryDB.write(elementsCollection.getElements())) {
            return Response.ok().build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    @Override
    @DELETE
    public Response clear() {
        if (memoryDB.clear())
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }


    //List<DBStorageElement> read(String key);
    @Override
    @Path("{key}/data")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("key") @NotBlank String key) {
        List<DBStorageElement> dbStorageElements = memoryDB.read(key);
        if (dbStorageElements == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(dbStorageElements).build();
    }

    @Override
    @Path("{key}/{timestamp}/data")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readElement(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp) {
        DateTime dt = parseDTFromString(timestamp);
        if (dt == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ////
        DBStorageElement dbStorageElement = memoryDB.read(key, dt);
        if (dbStorageElement == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(dbStorageElement).build();
    }

    //
    @Override
    @Path("{key}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response existsAny(@PathParam("key") @NotBlank String key) {
        if (memoryDB.exists(key))
            return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //
    @Override
    @Path("{key}/{timestamp}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exists(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp) {
        DateTime dt = parseDTFromString(timestamp);
        if (dt == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        ////
        if (memoryDB.exists(key, dt))
            return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //
    @Override
    @Path("{key}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("key") @NotBlank String key) {
        if (memoryDB.remove(key))
            return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //
    @Override
    @Path("{key}/{timestamp}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("key") @NotBlank String key, @PathParam("timestamp") @NotBlank String timestamp) {
        DateTime dt = parseDTFromString(timestamp);
        if (dt == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (memoryDB.remove(key, dt))
            return Response.ok().build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
