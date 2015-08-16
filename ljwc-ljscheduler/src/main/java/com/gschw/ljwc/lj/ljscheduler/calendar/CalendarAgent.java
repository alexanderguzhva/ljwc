package com.gschw.ljwc.lj.ljscheduler.calendar;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 8/16/15.
 */
public class CalendarAgent {
    private Identity agentIdentity;

    public Identity getAgentIdentity() {
        return agentIdentity;
    }

    public void setAgentIdentity(Identity agentIdentity) {
        this.agentIdentity = agentIdentity;
    }

    //
    private List<LJCalendarTask> tasksBeingProcessed;

    public List<LJCalendarTask> getTasksBeingProcessed() {
        return tasksBeingProcessed;
    }

    public void addTask(LJCalendarTask task) {
        tasksBeingProcessed.add(task);
    }

    //
    public CalendarAgent(Identity agentIdentity) {
        this.agentIdentity = agentIdentity;
        this.tasksBeingProcessed = new ArrayList<>();
    }
}
