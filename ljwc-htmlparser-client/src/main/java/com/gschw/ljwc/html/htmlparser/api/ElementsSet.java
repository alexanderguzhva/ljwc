package com.gschw.ljwc.html.htmlparser.api;

import com.gschw.ljwc.html.htmlparser.api.*;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by nop on 8/29/15.
 */
public class ElementsSet {
    public final Set<String> imageUrls;

    public ElementsSet() {
        imageUrls = new HashSet<>();
    }

    public void addElementsCollection(ElementsCollection collection) {
        for (ImageElement imageElement : collection.getImages())
            imageUrls.add(imageElement.src);
    }

}
