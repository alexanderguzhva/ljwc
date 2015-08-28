package com.gschw.ljwc.lj.gateway.servlet;

import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URLConnection;

/**
 * Created by nop on 8/22/15.
 */
public class GatewayServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(GatewayServlet.class);

    private SimpleDownloader simpleDownloader;

    private GatewayServletParameters parameters;

    public GatewayServlet(SimpleDownloader simpleDownloader, GatewayServletParameters parameters) {
        this.simpleDownloader = simpleDownloader;
        this.parameters = parameters;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Reading {}", req.getRequestURI());

        String uri = req.getRequestURI();
        if (uri == null || uri.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ////
        String suri = uri.substring(parameters.getRootUrl().length());
        logger.info("Short uri is {}", suri);
        byte[] data = simpleDownloader.download(suri);

        if (data == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ////
        String mimeType = URLConnection.guessContentTypeFromName(suri);

        if (mimeType != null && mimeType.equals(MediaType.TEXT_HTML))
        {
            //// quick and dirty
            String html = new String(data);
            html = html.replaceAll("http", parameters.getRootUrl() + "http");
            data = html.getBytes();
        }

        resp.setContentType(mimeType);
        //resp.setStatus(HttpServletResponse.SC_OK);
        resp.getOutputStream().write(data);
    }
}
