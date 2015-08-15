package com.gschw.ljwc.lj.ljcalendaragent.resources;

import com.gschw.ljwc.lj.ljcalendaragent.calendar.CalendarAnalyzer;
import com.gschw.ljwc.lj.ljcalendaragent.calendar.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 8/15/15.
 */
@Path("/controller")
public class ControllerResource {
    private static Logger logger = LoggerFactory.getLogger(ControllerResource.class);

    private Processor processor = new Processor();

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
