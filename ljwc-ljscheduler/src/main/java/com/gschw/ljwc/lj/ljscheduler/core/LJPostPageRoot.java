package com.gschw.ljwc.lj.ljscheduler.core;

import java.util.List;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageElement;
import org.hibernate.validator.constraints.URL;

/**
 * Created by nop on 7/24/15.
 *
 * This is a root page of LJ blog.
 */
public class LJPostPageRoot {
    /**
     * A list of elements in this page.
     */
    private List<LJPostPageElement> elements;

    /**
     * An URL.
     */
    @URL
    private String url;

    /**
     * Identifies this root page.
     */
    private Identity identity;

    public List<LJPostPageElement> getElements() {
        return elements;
    }

    public String getUrl() {
        return url;
    }

    /**
     * @return Returns an identity for this page.
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Sets the identity for this page.
     * @param identity Identity of this page.
     */
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public LJDownloadElement createLJDownloadElement() {
        return new LJDownloadElement(identity, url);
    }

}
