package com.gschw.ljwc.html.htmlparser.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
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
        this.images.add(image);
    }

    public void addImages(Collection<ImageElement> images) {
        this.images.addAll(images);
    }

    public void addImport(ImportElement importElement) {
        this.imports.add(importElement);
    }

    public void addImports(Collection<ImportElement> imports) {
        this.imports.addAll(imports);
    }

    public void addLink(LinkElement linkElement) {
        this.links.add(linkElement);
    }

    public void addLinks(Collection<LinkElement> links) {
        this.links.addAll(links);
    }

    public void addMedia(MediaElement mediaElement) {
        this.medias.add(mediaElement);
    }

    public void addMedias(Collection<MediaElement> medias) {
        this.medias.addAll(medias);
    }



    public List<ImageElement> getImages() {
        return images;
    }

    public List<ImportElement> getImports() {
        return imports;
    }

    public List<LinkElement> getLinks() {
        return links;
    }

    public List<MediaElement> getMedias() {
        return medias;
    }
}
