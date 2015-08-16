package com.gschw.ljwc.lj.ljscheduler.calendar;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResultElement;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by nop on 8/16/15.
 */
public class CalendarKeeper implements ILJCalendarTaskClient {
    private static Logger logger = LoggerFactory.getLogger(CalendarKeeper.class);

    private CalendarKeeperParameters parameters;

    private Queue<LJCalendarTask> calendarTasks;

    private Map<Identity, CalendarAgent> agents;

    private Map<Identity, CalendarAgent> tasksBeingProcessed;

    private final Object locker = new Object();

    public CalendarKeeper(CalendarKeeperParameters parameters) {
        this.parameters = parameters;

        this.calendarTasks = new ArrayDeque<>();
        this.agents = new HashMap<>();
        this.tasksBeingProcessed = new HashMap<>();
    }

    private CalendarAgent getOrCreateAgent(Identity clientIdentity) {
        CalendarAgent agent = agents.get(clientIdentity);
        if (agent == null) {
            agent = new CalendarAgent(clientIdentity);
            agents.put(clientIdentity, agent);
        }

        return agent;
    }

    @Override
    public LJCalendarTask acquireTask(Identity clientIdentity) {
        synchronized (locker) {
            if (calendarTasks.isEmpty())
                return null;

            ////
            LJCalendarTask task = calendarTasks.poll();

            CalendarAgent agent = getOrCreateAgent(clientIdentity);
            agent.addTask(task);

            tasksBeingProcessed.put(task.getTaskIdentity(), agent);

            return task;
        }
    }

    @Override
    public boolean complete(LJCalendarTaskResult result) {
        synchronized (locker) {
            if (result == null) {
                logger.error("result is complete");
                return false;
            }

            ////
            Identity taskIdentity = result.getTaskIdentity();
            CalendarAgent agent = tasksBeingProcessed.get(taskIdentity);
            if (agent == null) {
                logger.error("there is no agent match for a task {}", taskIdentity);
                return false;
            }

            //// list of links. Let us add
            for (LJCalendarTaskResultElement element : result.getElements()) {

            }
        }
        return false;
    }

    @Override
    public boolean download(LJCalendarTask task, long timeoutInMsec) {
        return false;
    }

    @Override
    public boolean download(LJCalendarTask task) {
        return download(task, parameters.getDefaultTimeoutMsec());
    }
}
