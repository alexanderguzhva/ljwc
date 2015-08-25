package com.gschw.ljwc.html.htmlparser.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.html.htmlparser.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by nop on 6/29/15.
 */

public class HTMLParserClient implements IHTMLParserClient {
    private static Logger logger = LoggerFactory.getLogger(HTMLParserClient.class);

    private Client client;

    private HTMLParserClientParameters parameters;

    public HTMLParserClient(Client client, HTMLParserClientParameters parameters) {
        this.client = client;
        this.parameters = parameters;
    }

    public ElementsCollection parse(byte[] data) {
        logger.info("Going to parse {} bytes", data.length);

        HTMLParseTaskByData parserData = new HTMLParseTaskByData(data);

        try {
            WebTarget target = client
                        .target(parameters.getServiceUrl())
                        .path("/parseByData");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = target
                            .request()
                            .buildPost(Entity.entity(parserData, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            HTMLParseResultByData result = response.readEntity(HTMLParseResultByData.class);
            if (result == null)
                return null;

            return result.getElements();
        }
        catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    @Override
    public ElementsCollection parse(String dbUrl) {
        logger.info("Going to parse {} db element", dbUrl);

        HTMLParseTaskByDBURL task = new HTMLParseTaskByDBURL(dbUrl);

        try {
            WebTarget target = client
                    .target(parameters.getServiceUrl())
                    .path("/parseByDBURL");

            logger.debug("Calling {}", target.getUri().toString());
            Response response = target
                            .request()
                            .buildPost(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE))
                            .invoke();

            logger.debug("{} returned {}", target.getUri().toString(), response.getStatusInfo());
            if (response.getStatus() != Response.Status.OK.getStatusCode())
                return null;

            HTMLParseResultByDBURL result = response.readEntity(HTMLParseResultByDBURL.class);
            if (result == null)
                return null;

            return result.getElements();
        }
        catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
