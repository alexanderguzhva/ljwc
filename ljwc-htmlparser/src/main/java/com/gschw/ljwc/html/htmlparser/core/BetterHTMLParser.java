package com.gschw.ljwc.html.htmlparser.core;

import com.gschw.ljwc.html.htmlparser.api.*;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Map;
import java.util.HashMap;


/**
 * Created by nop on 6/29/15.
 */
public class BetterHTMLParser {
    private static Logger logger = LoggerFactory.getLogger(BetterHTMLParser.class);


    private BetterHTMLParserParameters parameters;

    public BetterHTMLParser(BetterHTMLParserParameters parameters) {
        this.parameters = parameters;
    }

    //
    private void extractElements(Elements content, ElementsCollection outputElements) {
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
    }


    //



//    //
//    private Elements FindContent(Document doc) {
//        Elements content = null;
//
//        //// search for a content (tema.livejournal.com - style)
//        content = doc.select("article.b-singlepost-body");
//        if (content != null && content.size() != 0)
//            return content;
//
//        //// crustgroup.livejournal.com - style
//        content = doc.select("div.entry-content");
//        if (content != null && content.size() != 0)
//            return content;
//
//        //// botalex style
//        content = doc.select("div.entryText");
//        if (content != null && content.size() != 0)
//            return content;
//
//        //// karleev style
//        content = doc.select("div.asset-body");
//        if (content != null && content.size() != 0)
//            return content;
//
//        //// enview style
//        content = doc.select("div.j-e-text");
//        if (content != null && content.size() != 0)
//            return content;
//
//        //// humus style
//        content = doc.select("table.full_entry");
//        if (content != null && content.size() != 0)
//            return content;
//
//
//        return content;
//    }

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
    public ElementsCollection Process(Document doc) {
        ////
        ElementsCollection outputElements = new ElementsCollection();

        boolean bParsed = false;

        ////
        for (LJStyle ljStyle : parameters.getStyles()) {
            //// find a style match
            if (ljStyle.getName() == null || ljStyle.getName().isEmpty()) {
                logger.warn("bad ljStyle {} name", ljStyle.getDescription());
                continue;
            }

            Elements rootElements = doc.select(ljStyle.getName());
            if (rootElements == null || rootElements.size() == 0)
                continue;


            //// find body (main post)
            if (ljStyle.getBody() != null && (!ljStyle.getBody().isEmpty())) {

                Elements mainPostElements = rootElements.select(ljStyle.getBody());
                if (mainPostElements != null && mainPostElements.size() > 0) {
                    ////
                    bParsed = true;

                    //// process them
                    extractElements(mainPostElements, outputElements);

                    //// process comments
                    if (ljStyle.getComments() != null && (!ljStyle.getComments().isEmpty())) {
                        Elements commentsElements = rootElements.select(ljStyle.getComments());
                        if (commentsElements != null && commentsElements.size() > 0) {
                            extractElements(commentsElements, outputElements);
                        }
                    } else {
                        logger.info("No comments for {}", ljStyle.getDescription());
                    }
                }
            } else {
                logger.info("No body for {}", ljStyle.getDescription());
            }


            //// no more parsing
            if (bParsed)
                break;
        }

        if (!bParsed) {
            return null;
        }

        ////
        return outputElements;
    }
}
