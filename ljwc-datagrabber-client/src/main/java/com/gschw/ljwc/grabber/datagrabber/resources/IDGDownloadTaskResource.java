package com.gschw.ljwc.grabber.datagrabber.resources;

import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/12/15.
 */
public interface IDGDownloadTaskResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response doPost(@NotNull DGDownloadTask task);
}
