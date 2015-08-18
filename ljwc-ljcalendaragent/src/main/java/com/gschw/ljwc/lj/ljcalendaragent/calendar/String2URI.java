package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by nop on 8/17/15.
 */
public class String2URI {
    private String2URI() {
    }

    public static URI extractHost(String dirtyURL) throws MalformedURLException, URISyntaxException {
        URL url = new URL(dirtyURL);
        URI uri = new URI(url.getProtocol(), url.getHost(), null, null, null);
        return uri;
    }
}
