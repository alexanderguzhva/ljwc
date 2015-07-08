package com.gschw.ljwc.hbasewriter.client;

import java.net.URI;
import com.gschw.ljwc.hbaselib.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 * Created by nop on 6/22/15.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HBaseWriterClient {
    private static Logger logger = LoggerFactory.getLogger(DWApplication.class);

    private Client client;

    private URI serviceURI;

    public HBaseWriterClient(Client client, URI serviceURI) {
        this.client = client;
        this.serviceURI = serviceURI;
    }


    public void write(List<HBaseDataElement> elements) {

    }
}
