package com.gschw.ljwc.storage.hbasebridge.resources;

import com.gschw.ljwc.storage.IDBStorageResource;
import com.gschw.ljwc.storage.DBStorageElement;
import com.gschw.ljwc.storage.DBStorageElementsCollection;
import com.gschw.ljwc.storage.hbasebridge.core.HBaseDB;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

/**
 * Created by nop on 8/23/15.
 */
@Path("/")
public class DBResource implements IDBStorageResource {

    private HBaseDB hbaseDB;

    public DBResource(HBaseDB hbaseDB) {
        this.hbaseDB = hbaseDB;
    }

    //void write(DBStorageElement element);
    //void write(List<DBStorageElement> elements);
    //POST /
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response write(@NotNull DBStorageElementsCollection elementsCollection) {
        boolean b = hbaseDB.write(elementsCollection.getElements());
        if (!b)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }

    //void clear();
    //DELETE /
    @DELETE
    //@Path("/")
    public Response clear() {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

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
    public Response remove(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    //List<DBStorageElement> read();
    //GET /
    //@Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("skipData") Boolean skipData) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    //List<DBStorageElement> read(String key);
    //GET /{key}/element
    //
    //DBStorageElement read(String key, DateTime timestamp);
    //GET /{key}/element?timestamp=x
    @Path("{key}/element")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readElement(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        if (timestamp != null)
            return Response.status(Response.Status.NOT_IMPLEMENTED).build();

        List<DBStorageElement> elements = hbaseDB.read(key);
        if (elements == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        DBStorageElementsCollection collection = new DBStorageElementsCollection(elements);
        return Response.ok().entity(collection).build();
    }

    //DBStorageElement readLast(String key);
    //GET /{key}/lastelement
    @Path("{key}/lastelement")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readLast(@PathParam("key") @NotBlank String key) {
        DBStorageElement element = hbaseDB.readLast(key);
        if (element == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().entity(element).build();
    }

    //boolean exists(String key);
    //GET /{key}
    //
    //boolean exists(String key, DateTime timestamp);
    //GET /{key}?timestamp=x
    @Path("{key}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exists(@PathParam("key") @NotBlank String key, @QueryParam("timestamp") String timestamp) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }


}
