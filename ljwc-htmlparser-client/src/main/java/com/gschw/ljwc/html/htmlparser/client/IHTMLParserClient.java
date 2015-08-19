package com.gschw.ljwc.html.htmlparser.client;

import com.gschw.ljwc.html.htmlparser.api.ElementsCollection;

/**
 * Created by hadoop on 8/19/15.
 */
public interface IHTMLParserClient {
    ElementsCollection parse(byte[] data);
    ElementsCollection parse(String dbUrl);
}
