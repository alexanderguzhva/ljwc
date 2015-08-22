package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hadoop on 8/21/15.
 */
public class CalendarKeeper extends Keeper<LJCalendarTask, LJCalendarTaskResult, CalendarAgent> {
    private static Logger logger = LoggerFactory.getLogger(CalendarKeeper.class);

    @Override
    protected CalendarAgent createAgent(Identity clientIdentity) {
        return new CalendarAgent(clientIdentity);
    }

    @Override
    protected void processCompletedElement(LJCalendarTaskResult result) {
        for (LJCalendarTaskResultElement element : result.getElements()) {
            logger.info("{}: found {}", result.getTaskIdentity(), element.getUrl());
        }
    }
}
