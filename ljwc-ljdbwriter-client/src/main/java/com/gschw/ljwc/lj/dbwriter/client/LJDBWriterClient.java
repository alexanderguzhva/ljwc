package com.gschw.ljwc.lj.dbwriter.client;

import com.gschw.ljwc.grabber.datagrabber.api.*;
import com.gschw.ljwc.grabber.datagrabber.client.*;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 7/6/15.
 */
public class LJDBWriterClient implements IDataGrabberPushResultClient {
    private static Logger logger = LoggerFactory.getLogger(LJDBWriterClient.class);

    private Client client;

    private LJDBWriterClientParameters parameters;

    //
    public LJDBWriterClient(Client client, LJDBWriterClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    //
    public DataGrabberPushReply pushReply(DataGrabberIdentity identity, DataGrabberPushRequest request) {
        return null;
    }

}
