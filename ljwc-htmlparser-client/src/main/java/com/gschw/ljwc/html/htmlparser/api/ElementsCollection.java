package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 5/16/15.
 */
public class ElementsCollection {
    @JsonProperty("images")
    private List<ImageElement> images;

    @JsonProperty("imports")
    private List<ImportElement> imports;

    @JsonProperty("links")
    private List<LinkElement> links;

    @JsonProperty("medias")
    private List<MediaElement> medias;

    public ElementsCollection() {
        images = new ArrayList<>();
        imports = new ArrayList<>();
        links = new ArrayList<>();
        medias = new ArrayList<>();
    }



    public void addImage(ImageElement image) {
        images.add(image);
    }

    public void addImport(ImportElement importElement) {
        imports.add(importElement);
    }

    public void addLink(LinkElement linkElement) {
        links.add(linkElement);
    }

    public void addMedia(MediaElement mediaElement) {
        medias.add(mediaElement);
    }
}
