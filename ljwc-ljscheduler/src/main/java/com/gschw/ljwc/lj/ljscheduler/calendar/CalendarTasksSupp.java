package com.gschw.ljwc.lj.ljscheduler.calendar;

import com.gschw.lj.common.IResetEvent;
import com.gschw.lj.common.ManualResetEvent;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;


/**
 * Created by nop on 8/16/15.
 */
public class CalendarTasksSupp {
    private CalendarAgent agent;

    public CalendarAgent getAgent() {
        return agent;
    }

    public void setAgent(CalendarAgent agent) {
        this.agent = agent;
    }


    //
    private LJCalendarTask task;

    public LJCalendarTask getTask() {
        return task;
    }


    //
    private LJCalendarTaskResult result;

    public LJCalendarTaskResult getResult() {
        return result;
    }

    public void setResult(LJCalendarTaskResult result) {
        this.result = result;
    }

    //
    private IResetEvent mrEvent;

    public void complete() {
        mrEvent.set();
    }

    public boolean waitUntilComplete(Long timeoutMsec) throws InterruptedException {
        if (timeoutMsec == null) {
            mrEvent.waitOne();
            return true;
        }

        return mrEvent.waitOne(timeoutMsec.longValue());
    }

    //
    public CalendarTasksSupp(LJCalendarTask task) {
        this.task = task;

        this.mrEvent = new ManualResetEvent(false);
    }
}
