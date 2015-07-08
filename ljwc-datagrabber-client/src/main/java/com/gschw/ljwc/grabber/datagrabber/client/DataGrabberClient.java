package com.gschw.ljwc.grabber.datagrabber.client;

import com.gschw.ljwc.grabber.datagrabber.api.*;

import javax.ws.rs.client.Client;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 6/29/15.
 */
public class DataGrabberClient {
    private static Logger logger = LoggerFactory.getLogger(DataGrabberClient.class);

    private Client client;

    private URI serviceURI;

    public DataGrabberClient(Client client, URI serviceURI) {
        this.client = client;
        this.serviceURI = serviceURI;
    }

    public DataGrabberResult grab(DataGrabberRequest request) {

        return null;

    }
}
