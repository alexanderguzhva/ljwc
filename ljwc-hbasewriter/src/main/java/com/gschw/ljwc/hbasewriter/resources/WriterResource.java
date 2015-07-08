package com.gschw.ljwc.hbasewriter.resources;

import com.gschw.ljwc.hbaselib.HBaseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Created by nop on 6/22/15.
 */
@Path("/data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WriterResource {

    private HBaseWriter hBaseWriter;

    private static Logger logger = LoggerFactory.getLogger(WriterResource.class);



}
