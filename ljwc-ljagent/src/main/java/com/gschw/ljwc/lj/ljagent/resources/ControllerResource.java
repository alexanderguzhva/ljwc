package com.gschw.ljwc.lj.ljagent.resources;

import com.gschw.ljwc.lj.ljagent.core.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 7/20/15.
 */
@Path("/controller")
public class ControllerResource {
    private static Logger logger = LoggerFactory.getLogger(ControllerResource.class);

    private Processor processor;

    public ControllerResource(Processor processor) {
        this.processor = processor;
    }

    @POST
    @Path("iterate")
    public Response doIterate() {
        boolean result = processor.iterate();
        if (!result)
            return Response.serverError().build();

        return Response.ok().build();
    }
}
