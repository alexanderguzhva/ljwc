package com.gschw.ljwc.datagenerator.resources;

import com.gschw.ljwc.datagenerator.core.DataGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 7/2/15.
 */
@Path("/generate")
public class GeneratorResource {
    private static Logger logger = LoggerFactory.getLogger(GeneratorResource.class);

    private DataGenerator generator;

    public GeneratorResource(DataGenerator generator) {
        this.generator = generator;
    }

    @GET
    @Path("{filename}")
    public Response generate(@PathParam("filename") @NotBlank String filename) {
        byte[] generated = generator.generate();

        logger.debug("Generated %s, %d bytes", filename, generated.length);

        return Response
                .ok(generated, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .header("content-disposition",
                        String.format("attachment; filename = %s", filename))
                .build();
    }
}
