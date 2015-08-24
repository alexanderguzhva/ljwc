package com.gschw.ljwc.grabber.datagrabber.core;

import com.google.common.base.Throwables;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Downloads data.
 */
public class Grabber {
    private static Logger logger = LoggerFactory.getLogger(Grabber.class);

    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext httpContext;

    private GrabberParameters parameters;

    //
    public Grabber(GrabberParameters parameters) {
        this.parameters = parameters;

        cookieStore = new BasicCookieStore();

        httpClient =
                HttpClientBuilder
                        .create()
                        .setUserAgent(parameters.getUserAgentString())
                        .setDefaultCookieStore(cookieStore)
                        .build();

        httpContext = new BasicHttpContext();
    }

    //
    public Grabber(GrabberParameters parameters, GrabberSession grabberSession) {
        this.httpClient = grabberSession.getHttpClient();
        this.cookieStore = grabberSession.getCookieStore();
        this.httpContext = grabberSession.getHttpContext();
        this.parameters = parameters;
    }

    //
    public GrabberResult grab(String uri) {
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpGet, httpContext);
        }
        catch(org.apache.http.conn.ConnectTimeoutException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        } catch (ClientProtocolException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        } catch (IOException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }

        ////
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null)
            return null;

        //// content
        try {
            try (InputStream inputStream = httpEntity.getContent()) {

                ////
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[65536];
                    int len;
                    while ((len = inputStream.read(buffer)) > -1)
                        baos.write(buffer, 0, len);
                    baos.flush();

                    return new GrabberResult(uri, baos.toByteArray());
                }
            }
        } catch (IOException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
