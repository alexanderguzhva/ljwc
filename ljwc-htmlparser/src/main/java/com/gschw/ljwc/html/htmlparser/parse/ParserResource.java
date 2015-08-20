package com.gschw.ljwc.html.htmlparser.parse;

import com.gschw.ljwc.html.htmlparser.api.*;
import com.gschw.ljwc.html.htmlparser.core.BetterHTMLParser;

import com.gschw.ljwc.html.htmlparser.core.SimpleDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by nop on 6/29/15.
 */
@Path("/")
public class ParserResource {
    private static Logger logger = LoggerFactory.getLogger(ParserResource.class);

    private SimpleDownloader downloader;

    public ParserResource(SimpleDownloader downloader) {
        this.downloader = downloader;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/parseByData")
    public Response parseByData(HTMLParseTaskByData parserData) {
        Document document;

        try (ByteArrayInputStream bs = new ByteArrayInputStream(parserData.getData())){
            document = Jsoup.parse(bs, "UTF-8", "");
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        ////
        ElementsCollection elementsCollection = BetterHTMLParser.Process(document);
        if (elementsCollection == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        //
        HTMLParseResultByData result = new HTMLParseResultByData();
        result.setElements(elementsCollection);

        return Response.ok().entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/parseByDBURL")
    public Response parseByDBURL(HTMLParseTaskByDBURL parserData) {
        String url = parserData.getUrl();

        logger.info("Going to parse by DB URL {}", url);

        byte[] data = downloader.download(url);
        if (data == null) {
            logger.info("Null data for {}", url);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Document document;

        try (ByteArrayInputStream bs = new ByteArrayInputStream(data)){
            document = Jsoup.parse(bs, "UTF-8", "");
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        ////
        ElementsCollection elementsCollection = BetterHTMLParser.Process(document);
        if (elementsCollection == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        //
        HTMLParseResultByDBURL result = new HTMLParseResultByDBURL();
        result.setElements(elementsCollection);

        return Response.ok().entity(result).build();
    }

}
