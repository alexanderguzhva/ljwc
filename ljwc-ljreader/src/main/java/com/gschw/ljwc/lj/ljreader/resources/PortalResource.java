package com.gschw.ljwc.lj.ljreader.resources;

import com.gschw.ljwc.storage.DBStorageElement;
import com.gschw.ljwc.storage.client.DBStorageClient;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/20/15.
 */
@Path("/")
public class PortalResource {
    private static Logger logger = LoggerFactory.getLogger(PortalResource.class);

    private DBStorageClient dbStorageClient;

    public PortalResource(DBStorageClient dbStorageClient) {
        this.dbStorageClient = dbStorageClient;
    }

    @GET
    @Path("{filename}")
    public Response doGet(@PathParam("filename") @NotBlank String filename) {
        logger.info("Retrieving {}", filename);

        DBStorageElement element = dbStorageClient.readLast(filename);
        if (element == null)
            return Response.noContent().build();

        byte[] data = element.getData().get("d");
        if (data == null)
            return Response.noContent().build();

        return Response
                .ok(data, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .header("content-disposition",
                        String.format("attachment; filename = %s", filename))
                .build();
    }
}
