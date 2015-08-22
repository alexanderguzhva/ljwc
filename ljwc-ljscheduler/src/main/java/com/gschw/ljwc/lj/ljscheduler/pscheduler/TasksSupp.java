package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.lj.common.IResetEvent;
import com.gschw.lj.common.ManualResetEvent;

/**
 * Created by hadoop on 8/21/15.
 */
public class TasksSupp<T, U> {
    private Agent<T> agent;

    public Agent<T> getAgent() {
        return agent;
    }

    public void setAgent(Agent<T> agent) {
        this.agent = agent;
    }


    //
    private T task;

    public T getTask() {
        return task;
    }


    //
    private U result;

    public U getResult() {
        return result;
    }

    public void setResult(U result) {
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
    public TasksSupp(T task) {
        this.task = task;

        this.mrEvent = new ManualResetEvent(false);
    }
}
