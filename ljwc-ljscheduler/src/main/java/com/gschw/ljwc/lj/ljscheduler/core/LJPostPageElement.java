package com.gschw.ljwc.lj.ljscheduler.core;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/24/15.
 *
 * Represents an element of a page (image, etc.)
 */
public class LJPostPageElement {
    /**
     * An url
     */
    @URL
    private String url;

    /**
     * An identity.
     */
    @NotNull
    private Identity identity;

    /**
     * @return URL of an element.
     */
    public String getUrl() {
        return url;
    }

    public LJDownloadElement createLJDownloadElement() {
        return new LJDownloadElement(identity, url);
    }

}
