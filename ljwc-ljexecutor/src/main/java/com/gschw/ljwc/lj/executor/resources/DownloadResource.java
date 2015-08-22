package com.gschw.ljwc.lj.executor.resources;

import com.gschw.ljwc.lj.executor.core.X;
import org.hibernate.validator.constraints.NotBlank;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/22/15.
 */
@Path("/download")
public class DownloadResource {
    private X x;

    public DownloadResource(X x) {
        this.x = x;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response download(@QueryParam("rootpage") @NotBlank String rootPage) {
        x.downloadAll(rootPage);

        return Response.ok().build();
    }
}
