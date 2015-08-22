package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;

/**
 * Created by hadoop on 8/21/15.
 */
public class SinglePageAgent extends Agent<LJSinglePageTask> {
    public SinglePageAgent(Identity agentIdentity) {
        super(agentIdentity);
    }
}
