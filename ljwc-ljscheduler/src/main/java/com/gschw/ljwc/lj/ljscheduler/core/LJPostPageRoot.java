package com.gschw.ljwc.lj.ljscheduler.core;

import java.util.List;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageElement;

/**
 * Created by nop on 7/24/15.
 */
public class LJPostPageRoot {
    private List<LJPostPageElement> elements;

    private String url;

    private Identity identity;

    public List<LJPostPageElement> getElements() {
        return elements;
    }

    public String getUrl() {
        return url;
    }
}
