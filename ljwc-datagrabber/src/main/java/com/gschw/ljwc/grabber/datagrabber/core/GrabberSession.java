package com.gschw.ljwc.grabber.datagrabber.core;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Created by nop on 7/12/15.
 */
public class GrabberSession {
    private HttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext httpContext;

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    private GrabberSession() {
    }

    public static GrabberSession createSession(GrabberParameters parameters) {
        GrabberSession session = new GrabberSession();

        session.cookieStore = new BasicCookieStore();

        session.httpClient =
                HttpClientBuilder
                        .create()
                        .setUserAgent(parameters.getUserAgentString())
                        .setDefaultCookieStore(session.cookieStore)
                        .build();

        session.httpContext = new BasicHttpContext();

        return session;
    }
}
