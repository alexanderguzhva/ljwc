package com.gschw.ljwc.uploader.resources;

import com.gschw.ljwc.storage.DBStorageElement;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;

import javax.validation.constraints.NotNull;

import com.gschw.ljwc.uploader.api.DGUploadTask;

import com.gschw.ljwc.storage.client.DBStorageClient;

/**
 * Created by nop on 7/13/15.
 */
@Path("/")
public class UploaderResource implements IDGUploaderResource {
    private static Logger logger = LoggerFactory.getLogger(UploaderResource.class);

    private DBStorageClient dbStorageClient;

    public UploaderResource(DBStorageClient dbStorageClient) {
        this.dbStorageClient = dbStorageClient;
    }

    @Override
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upload(@NotNull DGUploadTask uploadTask) {
        logger.info("Got a request to upload url = {}, id = {}, {} bytes",
                uploadTask.getUrl(),
                uploadTask.getTaskIdentity(),
                uploadTask.getData().length);

        DBStorageElement dbStorageElement = new DBStorageElement(uploadTask.getUrl());
        dbStorageElement.setTimestamp(DateTime.now());
        dbStorageElement.getData().put("d", uploadTask.getData());

        boolean result = dbStorageClient.write(dbStorageElement);
        if (!result)
            return Response.status(Response.Status.BAD_REQUEST).build();

        return Response.ok().build();
    }
}
