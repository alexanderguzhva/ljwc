package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;

/**
 * Created by hadoop on 8/21/15.
 */
public class CalendarAgent extends Agent<LJCalendarTask> {
    public CalendarAgent(Identity agentIdentity) {
        super(agentIdentity);
    }
}
