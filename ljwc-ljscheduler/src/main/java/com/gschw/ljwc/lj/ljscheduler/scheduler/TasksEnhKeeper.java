package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.client.ILJDownloadTaskClient;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;

/**
 * Created by nop on 7/24/15.
 */
public class TasksEnhKeeper implements ILJDownloadTaskClient {
    private static Logger logger = LoggerFactory.getLogger(TasksEnhKeeper.class);

    private TasksEnhKeeperParameters parameters;


    private Map<Identity, DownloadSequence> elementsBeingDownloaded;
    private Queue<LJPostPageRoot> rootPagesToProcess;



    @Override
    public LJDownloadTask acquireTask(Identity clientIdentity) {

    }

    @Override
    public boolean completeElement(Identity elementIdentity, boolean success) {
        if (elementIdentity == null)
            return false;

        DownloadSequence sequence = elementsBeingDownloaded.get(elementIdentity);
        if (sequence == null) {
            logger.info("Unknown element {} to match", elementIdentity);
            return false;
        }

        boolean result = sequence.completeElement(elementIdentity, success);
        return false;
    }
}
