package com.gschw.ljwc.lj.ljscheduler.api;

import com.gschw.ljwc.auth.Identity;

/**
 * Created by hadoop on 8/21/15.
 */
public interface ILJTaskIdentifiable {
    Identity getTaskIdentity();
}
