package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nop on 8/12/15.
 */
public class LinksMatcher {
    private static Logger logger = LoggerFactory.getLogger(LinksMatcher.class);


    private LinksMatcher() {
    }

    //
    public static void MatchLinks(Document doc, Pattern pattern, Collection<String> producedLinks) {
        ////
        //Elements content = FindContent(doc);
        //if (content == null)
        //    return;

        ////
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String href = link.attr("abs:href");
            if (href.isEmpty())
                continue;

            Matcher m = pattern.matcher(href);
            if (m.matches())
                producedLinks.add(href);
        }
    }

}
