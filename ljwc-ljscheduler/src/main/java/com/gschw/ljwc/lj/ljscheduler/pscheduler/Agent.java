package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.ljwc.auth.Identity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 8/21/15.
 */
public class Agent<T> {
    private Identity agentIdentity;

    public Identity getAgentIdentity() {
        return agentIdentity;
    }

    public void setAgentIdentity(Identity agentIdentity) {
        this.agentIdentity = agentIdentity;
    }

    //
    private List<T> tasksBeingProcessed;

    public List<T> getTasksBeingProcessed() {
        return tasksBeingProcessed;
    }

    public void addTask(T task) {
        tasksBeingProcessed.add(task);
    }

    public boolean removeTask(T task) {
        return tasksBeingProcessed.remove(task);
    }

    //
    public Agent(Identity agentIdentity) {
        this.agentIdentity = agentIdentity;
        this.tasksBeingProcessed = new ArrayList<>();
    }
}
