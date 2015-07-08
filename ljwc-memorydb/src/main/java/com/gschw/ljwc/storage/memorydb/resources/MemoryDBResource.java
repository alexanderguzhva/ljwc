package com.gschw.ljwc.storage.memorydb.resources;

import com.gschw.ljwc.storage.memorydb.core.MemoryDB;

import com.gschw.ljwc.storage.StorageReadOperationResult;
import com.gschw.ljwc.storage.StorageWriteOperationResult;
import com.gschw.ljwc.storage.StorageElementCollection;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created by nop on 6/24/15.
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MemoryDBResource {
    private MemoryDB memoryDB;

    public MemoryDBResource(MemoryDB memoryDB) {
        this.memoryDB = memoryDB;
    }

    @GET
    @Path("{keyAddress}")
    public StorageReadOperationResult doGet(@PathParam("keyAddress") byte[] keyAddress) {
        return memoryDB.read(keyAddress);
    }

    @POST
    @Path("/")
    public StorageWriteOperationResult doPost(StorageElementCollection elements) {
        return memoryDB.write(elements);
    }
}
