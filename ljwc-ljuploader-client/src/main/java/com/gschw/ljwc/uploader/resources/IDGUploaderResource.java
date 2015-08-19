package com.gschw.ljwc.uploader.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.constraints.NotNull;

import com.gschw.ljwc.uploader.api.DGUploadTask;

/**
 * Created by nop on 7/13/15.
 */
public interface IDGUploaderResource {
    @POST
    //@Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    Response upload(@NotNull DGUploadTask uploadTask);
}
