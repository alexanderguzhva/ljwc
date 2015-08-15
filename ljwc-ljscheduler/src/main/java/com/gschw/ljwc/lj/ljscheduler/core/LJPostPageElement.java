package com.gschw.ljwc.lj.ljscheduler.core;

import com.gschw.ljwc.auth.Identity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by nop on 7/24/15.
 */
public class LJPostPageElement {
    @URL
    @NotBlank
    private String url;

    @NotNull
    private Identity identity;

    public String getUrl() {
        return url;
    }
}
