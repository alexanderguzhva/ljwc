package com.gschw.ljwc.lj.ljscheduler.core;

import java.util.List;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageElement;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/24/15.
 */
public class LJPostPageRoot {
    private List<LJPostPageElement> elements;

    @URL
    @NotBlank
    private String url;

    @NotNull
    private Identity identity;

    public List<LJPostPageElement> getElements() {
        return elements;
    }

    public String getUrl() {
        return url;
    }
}
