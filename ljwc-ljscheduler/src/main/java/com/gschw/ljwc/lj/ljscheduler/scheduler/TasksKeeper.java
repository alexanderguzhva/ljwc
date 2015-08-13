package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.client.ILJDownloadTaskClient;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.ArrayDeque;

/**
 * Created by nop on 7/19/15.
 */
public class TasksKeeper implements ILJDownloadTaskClient {
    private static Logger logger = LoggerFactory.getLogger(TasksKeeper.class);

    private TasksKeeperParameters parameters;

    public TasksKeeper(TasksKeeperParameters parameters) {
        this.parameters = parameters;

        testTasksToProcess = new ArrayDeque<>(parameters.getTasksToProcess());
    }


    //
    private Queue<LJDownloadTask> testTasksToProcess;

    //
    @Override
    public LJDownloadTask acquireTask(Identity clientIdentity) {
        if (clientIdentity == null)
            return null;

        ////
        LJDownloadTask task;
        synchronized(testTasksToProcess) {
            if (testTasksToProcess.isEmpty())
                return null;

            task = testTasksToProcess.poll();
        }

        task.setAssignedTo(clientIdentity);

        logger.info("Issued {} to {}", task.getIdentity(), task.getAssignedTo());

        return task;
    }

    @Override
    public boolean completeElement(Identity elementIdentity, boolean success) {
        logger.info("Complete says: {} for {}", success, elementIdentity);
        return false;
    }


    @Override
    public boolean download(LJDownloadTask task, long timeoutInMsec) {
        synchronized (testTasksToProcess) {
            testTasksToProcess.add(task);
        }

        //// wait
    }
}
