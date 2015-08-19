package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.client.ILJDownloadTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 7/15/15.
 * This is an active element that periodically pulls tasks from
 *   ljscheduler and coordinates datagrabbers to download them.
 */
public class Processor {
    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private IDGDownloadTaskClient dgDownloadClient;
    private ILJDownloadTaskClient iljDownloadTaskClient;

    private ProcessorParameters parameters;

    public Processor(IDGDownloadTaskClient dgDownloadClient, ILJDownloadTaskClient iljDownloadTaskClient, ProcessorParameters parameters) {
        this.dgDownloadClient = dgDownloadClient;
        this.iljDownloadTaskClient = iljDownloadTaskClient;
        this.parameters = parameters;
    }

    // note: this procedure should be able to process errors from
    //  datagrabbers (as they may go down or limit the number
    //  of cncurrent HTTP connections.
    private void process() {
        //// to be done later
    }


    //
    private boolean iterate(Identity dgClientSession) {
        //// grab a task
        LJDownloadTask task = iljDownloadTaskClient.acquireTask(parameters.getProcessorIdentity());
        if (task == null) {
            logger.info("Could not get a task to process");
            return false;
        }

        if (task.getElements().size() == 0) {
            logger.info("Empty task list");
            return false;
        }

        if (!parameters.getProcessorIdentity().equals(task.getAssignedTo())) {
            logger.error("wtf, I am {} and task identity is {}", parameters.getProcessorIdentity(), task.getAssignedTo());
            return false;
        }

        //// ok, process one by one
        for (LJDownloadElement element : task.getElements()) {
            //// let this single grabber to process it
            DGDownloadTask downloadTask = new DGDownloadTask();
            downloadTask.setTaskIdentity(element.getIdentity());
            downloadTask.setUrl(element.getUrl());
            downloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
            downloadTask.setReturnDataInReply(true);
            downloadTask.setUploadDataToBase(true);

            ////
            boolean bSuccessToReturn = false;
            DGDownloadResult result = dgDownloadClient.download(dgClientSession, downloadTask);
            if (result == null) {
                logger.error("wtf, no result");

                //// mark this result as failed
                bSuccessToReturn = false;
            }
            else {
                //// result is not null
                if (result.getTaskIdentity() == null) {
                    logger.error("do not know how to identify this task");
                    continue;
                }

                if (!result.getTaskIdentity().equals(element.getIdentity())) {
                    logger.error("the identity of the result does not match the identity of the element!");
                    continue;
                }

                bSuccessToReturn = result.isUploadSuccess();
            }


            //// tell the boss about the result
            boolean bComplete = iljDownloadTaskClient.completeElement(element.getIdentity(), bSuccessToReturn);
            if (!bComplete) {
                logger.error("Could not tell scheduler about the result");
            }

            //// anyway, there is nothing we should do at this point.
            ////   maybe, there will be a mechanism that adds
            ////   several attempts.
        }

        return true;
    }

    //
    public boolean iterate() {
        Identity dgClientSession = dgDownloadClient.createSession();
        if (dgClientSession == null) {
            logger.error("Could not create a session");
            return false;
        }

        boolean bIterate = iterate(dgClientSession);

        if (dgClientSession != null) {
            boolean result = dgDownloadClient.deleteSession(dgClientSession);
            if (!result) {
                logger.error("Could not delete a session");
                return false;
            }
        }

        return bIterate;
    }

}
