package com.gschw.ljwc.html.htmlparser.core;

import com.gschw.ljwc.html.htmlparser.api.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nop on 6/29/15.
 */
public class BetterHTMLParser {
    private BetterHTMLParser() {
    }

    //
    private static Elements FindContent(Document doc) {
        Elements content = null;

        //// search for a content (tema.livejournal.com - style)
        content = doc.select("article.b-singlepost-body");
        if (content != null && content.size() != 0)
            return content;

        //// crustgroup.livejournal.com - style
        content = doc.select("div.entry-content");
        if (content != null && content.size() != 0)
            return content;

        //// botalex style
        content = doc.select("div.entryText");
        if (content != null && content.size() != 0)
            return content;

        //// karleev style
        content = doc.select("div.asset-body");
        if (content != null && content.size() != 0)
            return content;

        //// enview style
        content = doc.select("div.j-e-text");
        if (content != null && content.size() != 0)
            return content;

        //// humus style
        content = doc.select("table.full_entry");
        if (content != null && content.size() != 0)
            return content;


        return content;
    }

    //
    private static void MatchLinks(Document doc, Pattern pattern, Collection<String> producedLinks) {
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

    //
    //
    public static ElementsCollection Process(Document doc) {
        Elements content = FindContent(doc);
        if (content == null)
            return null;

        ////
        ElementsCollection outputElements = new ElementsCollection();

        ////
        Elements links = content.select("a[href]");
        Elements media = content.select("[src]");
        Elements imports = content.select("link[href]");

        ////
        for (Element src : media) {
            if (src.tagName().equals("img")) {
                ImageElement mediaElement = new ImageElement();
                mediaElement.width = src.attr("width");
                mediaElement.height = src.attr("height");
                mediaElement.src = src.attr("abs:src");
                mediaElement.tag = src.tagName();
                mediaElement.alt = src.attr("alt");
                outputElements.addImage(mediaElement);
            }
            else {
                MediaElement mediaElement = new MediaElement();
                mediaElement.src = src.attr("abs:src");
                mediaElement.tag = src.tagName();
                outputElements.addMedia(mediaElement);
            }
        }

        ////
        for (Element link : imports) {
            ImportElement importElement = new ImportElement();
            importElement.href = link.attr("abs:href");
            importElement.rel = link.attr("rel");
            importElement.tag = link.tagName();
            outputElements.addImport(importElement);
        }

        for (Element link : links) {
            LinkElement linkElement = new LinkElement();
            linkElement.tag = link.tagName();
            linkElement.text = link.text();
            linkElement.href = link.attr("abs:href");
            linkElement.rel = link.attr("rel");
            outputElements.addLink(linkElement);
        }

        ////
        return outputElements;
    }
}
