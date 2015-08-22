package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.ILJTaskIdentifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by hadoop on 8/21/15.
 */
public abstract class Keeper<
        T extends ILJTaskIdentifiable,
        U extends ILJTaskIdentifiable,
        V extends Agent<T>> {
    private static Logger logger = LoggerFactory.getLogger(Keeper.class);

    private Queue<Identity> tasksQueue;
    private Map<Identity, TasksSupp<T, U>> tasksSupp;

    private Map<Identity, V> agents;

    private final Object locker = new Object();

    protected Keeper() {
        this.tasksQueue = new ArrayDeque<>();
        this.agents = new HashMap<>();
        this.tasksSupp = new HashMap<>();
    }

    protected abstract V createAgent(Identity clientIdentity);

    private V getOrCreateAgent(Identity clientIdentity) {
        V agent = agents.get(clientIdentity);
        if (agent == null) {
            agent = createAgent(clientIdentity);
            agents.put(clientIdentity, agent);
        }

        return agent;
    }

    public T acquireTask(Identity clientIdentity) {
        synchronized (locker) {
            if (tasksQueue.isEmpty())
                return null;

            ////
            Identity nextTaskIdentity = tasksQueue.poll();
            TasksSupp<T, U> supp = tasksSupp.get(nextTaskIdentity);
            if (supp == null) {
                logger.error("wtf, tasksSupp does not contain corresponding identity");
                return null;
            }

            ////
            V agent = getOrCreateAgent(clientIdentity);
            supp.setAgent(agent);

            agent.addTask(supp.getTask());

            return supp.getTask();
        }
    }

    public boolean complete(U result) {
        TasksSupp<T, U> supp;

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

            //// task is no longer being processed
            Agent<T> agent = supp.getAgent();
            supp.setAgent(null);
            if (!agent.removeTask(supp.getTask())) {
                logger.error("agent knows nothing about a task {}", taskIdentity);
            }
        }

        try {
            processCompletedElement(result);
        }
        finally {
            supp.complete();
        }
        return true;
    }

    protected abstract void processCompletedElement(U result);

    private U internalDownload(T task, Long timeoutInMsec) {
        TasksSupp<T, U> supp;

        synchronized (locker) {
            Identity taskIdentity = task.getTaskIdentity();
            supp = tasksSupp.get(taskIdentity);
            if (supp == null) {
                //// there is no such a task exists
                //// create supp
                supp = new TasksSupp<>(task);
                tasksSupp.put(task.getTaskIdentity(), supp);

                //// add to queue
                tasksQueue.add(taskIdentity);
            }
        }

        ////
        try {
            boolean b = supp.waitUntilComplete(timeoutInMsec);
            if (!b)
                return null;

            return supp.getResult();
        } catch(InterruptedException e) {
            logger.error(Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    public U download(T task, long timeoutInMsec) {
        return internalDownload(task, new Long(timeoutInMsec));
    }

    public U download(T task) {
        return internalDownload(task, null);
    }

}
