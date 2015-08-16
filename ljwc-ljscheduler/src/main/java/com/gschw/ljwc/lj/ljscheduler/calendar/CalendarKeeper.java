package com.gschw.ljwc.lj.ljscheduler.calendar;

import com.google.common.base.Throwables;
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

    private Queue<Identity> calendarTasksQueue;
    private Map<Identity, CalendarTasksSupp> tasksSupp;

    private Map<Identity, CalendarAgent> agents;



    private final Object locker = new Object();

    public CalendarKeeper(CalendarKeeperParameters parameters) {
        this.parameters = parameters;

        this.calendarTasksQueue = new ArrayDeque<>();
        this.agents = new HashMap<>();
        this.tasksSupp = new HashMap<>();
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
            if (calendarTasksQueue.isEmpty())
                return null;

            ////
            Identity nextTaskIdentity = calendarTasksQueue.poll();
            CalendarTasksSupp supp = tasksSupp.get(nextTaskIdentity);
            if (supp == null) {
                logger.error("wtf, tasksSupp does not contain corresponding identity");
                return null;
            }

            ////
            CalendarAgent agent = getOrCreateAgent(clientIdentity);
            supp.setAgent(agent);

            agent.addTask(supp.getTask());

            return supp.getTask();
        }
    }

    @Override
    public boolean complete(LJCalendarTaskResult result) {
        CalendarTasksSupp supp;

        synchronized (locker) {
            if (result == null) {
                logger.error("result is complete");
                return false;
            }

            ////
            Identity taskIdentity = result.getTaskIdentity();
            supp = tasksSupp.get(taskIdentity);
            if (supp == null) {
                logger.error("there is no matching supp for the task {}", taskIdentity);
                return false;
            }

            //// save result
            supp.setResult(result);

            //// debug: enumerate result
            for (LJCalendarTaskResultElement element : result.getElements()) {
                logger.info("{}: found {}", taskIdentity, element.getUrl());
            }

            //// task is no longer being processed
            CalendarAgent agent = supp.getAgent();
            supp.setAgent(null);
            if (!agent.removeTask(supp.getTask())) {
                logger.error("agent knows nothing about a task {}", taskIdentity);
            }
        }

        supp.complete();
        return true;
    }

    private boolean internalDownload(LJCalendarTask task, Long timeoutInMsec) {
        CalendarTasksSupp supp;

        synchronized (locker) {
            Identity taskIdentity = task.getTaskIdentity();
            supp = tasksSupp.get(taskIdentity);
            if (supp == null) {
                //// there is no such a task exists
                //// create supp
                supp = new CalendarTasksSupp(task);
                tasksSupp.put(task.getTaskIdentity(), supp);

                //// add to queue
                calendarTasksQueue.add(taskIdentity);
            }
        }

        ////
        try {
            boolean b = supp.waitUntilComplete(timeoutInMsec);
            return b;
        } catch(InterruptedException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return false;
        }
    }


    @Override
    public boolean download(LJCalendarTask task, long timeoutInMsec) {
        return internalDownload(task, new Long(timeoutInMsec));
    }

    @Override
    public boolean download(LJCalendarTask task) {
        return internalDownload(task, null);
    }
}
