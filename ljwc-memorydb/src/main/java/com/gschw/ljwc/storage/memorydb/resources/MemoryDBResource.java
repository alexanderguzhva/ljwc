package com.gschw.ljwc.storage.memorydb.resources;

import com.gschw.ljwc.storage.*;
import com.gschw.ljwc.storage.memorydb.core.MemoryDB;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;

import com.gschw.ljwc.storage.memorydb.core.MemoryDBSettings;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nop on 6/24/15.
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MemoryDBResource implements IDBStorageResource {
    private static Logger logger = LoggerFactory.getLogger(MemoryDBResource.class);

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
        logger.info("write");

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
        logger.info("clear");

        if (memoryDB.clear())
            return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("skipData") Boolean skipData) {
        logger.info("read, skipData is {}", skipData);

        if (skipData == null || skipData == false) {
            //// returns elements with data
            List<DBStorageElement> dbStorageElements = memoryDB.read();
            if (dbStorageElements == null)
                return Response.status(Response.Status.NO_CONTENT).build();

            return Response.ok().entity(dbStorageElements).build();
        } else {
            //// skipdata = true
            List<DBStorageElement> dbStorageElements = memoryDB.readWithoutData();
            if (dbStorageElements == null)
                return Response.status(Response.Status.NO_CONTENT).build();

            return Response.ok().entity(dbStorageElements).build();
        }

    }



    @Override
    @Path("{key}/element")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readElement(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        logger.info("readElement: key {}, timestamp {}", key, timestamp);

        if (timestamp == null || timestamp.isEmpty()) {
            //// exact time is not known, so read everything
            List<DBStorageElement> dbStorageElements = memoryDB.read(key);
            if (dbStorageElements == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok().entity(dbStorageElements).build();
        }
        else {
            //// exact time is known
            DateTime dt = parseDTFromString(timestamp);
            if (dt == null)
                return Response.status(Response.Status.BAD_REQUEST).build();

            ////
            DBStorageElement dbStorageElement = memoryDB.read(key, dt);
            if (dbStorageElement == null)
                return Response.status(Response.Status.NOT_FOUND).build();

            return Response.ok().entity(dbStorageElement).build();
        }
    }


    //DBStorageElement readLast(String key);
    @Path("{key}/lastelement")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLast(@PathParam("key") @NotBlank String key) {
        logger.info("readLast: key {}", key);

        DBStorageElement dbStorageElement = memoryDB.readLast(key);
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
    public Response exists(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        logger.info("exists: key {}, timestamp {}", key, timestamp);

        if (timestamp == null || timestamp.isEmpty()) {
            //// exact time is not known
            if (memoryDB.exists(key))
                return Response.ok().build();

            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            //// exact time is known
            DateTime dt = parseDTFromString(timestamp);
            if (dt == null)
                return Response.status(Response.Status.BAD_REQUEST).build();

            ////
            if (memoryDB.exists(key, dt))
                return Response.ok().build();

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    //
    @Override
    @Path("{key}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        logger.info("remove: key {}, timestamp {}", key, timestamp);

        if (timestamp == null || timestamp.isEmpty()) {
            //// exact time is not known
            if (memoryDB.remove(key))
                return Response.ok().build();

            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            //// exact time is known
            DateTime dt = parseDTFromString(timestamp);
            if (dt == null)
                return Response.status(Response.Status.BAD_REQUEST).build();

            if (memoryDB.remove(key, dt))
                return Response.ok().build();

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
