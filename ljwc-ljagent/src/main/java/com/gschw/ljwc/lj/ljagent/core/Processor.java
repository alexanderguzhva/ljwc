package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.html.htmlparser.api.ElementsCollection;
import com.gschw.ljwc.html.htmlparser.api.HTMLParseResultByDBURL;
import com.gschw.ljwc.html.htmlparser.api.ImageElement;
import com.gschw.ljwc.html.htmlparser.client.IHTMLParserClient;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageElement;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageElementCategory;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;
import com.gschw.ljwc.lj.ljscheduler.client.ILJSinglePageTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJSinglePageTaskClient;
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
    private ILJSinglePageTaskClient ljSinglePageTaskClient;
    private IHTMLParserClient htmlParserClient;

    private ProcessorParameters parameters;

    public Processor(
            IDGDownloadTaskClient dgDownloadClient,
            ILJSinglePageTaskClient ljSinglePageTaskClient,
            IHTMLParserClient htmlParserClient,
            ProcessorParameters parameters) {
        this.dgDownloadClient = dgDownloadClient;
        this.ljSinglePageTaskClient = ljSinglePageTaskClient;
        this.htmlParserClient = htmlParserClient;
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
        LJSinglePageTask task = ljSinglePageTaskClient.acquireTask(parameters.getProcessorIdentity());
        if (task == null) {
            logger.info("Could not get a task to process");
            return false;
        }

        if (!parameters.getProcessorIdentity().equals(task.getAssignedTo())) {
            logger.error("wtf, I am {} and task identity is {}", parameters.getProcessorIdentity(), task.getAssignedTo());
            return false;
        }

        //// so, an order of operations is the following:
        //// 1. download file and place it in db
        //// 2. ask htmlparser to parse a file and return a list of links to be downloaded
        //// 3. download links and store them in db
        LJSinglePageTaskResult spResult = new LJSinglePageTaskResult(task.getTaskIdentity());


        //// let this single grabber to process it
        DGDownloadTask downloadTask = new DGDownloadTask();
        downloadTask.setTaskIdentity(task.getTaskIdentity());
        downloadTask.setUrl(task.getUrl());
        downloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
        downloadTask.setReturnDataInReply(false);
        downloadTask.setUploadDataToBase(true);

        //// step 1
        DGDownloadResult result = dgDownloadClient.download(dgClientSession, downloadTask);

        boolean bSuccessToReturn = false;
        try {
            if (result == null) {
                logger.info("Could not download {}", task.getUrl());
                return false;
            }

            //// add to result
            LJSinglePageElement elementPage = new LJSinglePageElement(task.getUrl(), LJSinglePageElementCategory.PAGE);
            spResult.addElement(elementPage);

            //// ok, step 2
            ElementsCollection parsedElements = htmlParserClient.parse(task.getUrl());
            if (parsedElements == null) {
                logger.info("Could not parse elements for {}", task.getUrl());
                return false;
            }

            //// download images
            for (ImageElement imageElement : parsedElements.getImages()) {
                DGDownloadTask elementDownloadTask = new DGDownloadTask();
                elementDownloadTask.setTaskIdentity(task.getTaskIdentity());
                elementDownloadTask.setUrl(imageElement.src);
                elementDownloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
                elementDownloadTask.setReturnDataInReply(false);
                elementDownloadTask.setUploadDataToBase(true);

                DGDownloadResult localResult = dgDownloadClient.download(dgClientSession, elementDownloadTask);
                if (localResult == null) {
                    logger.info("Could not download {}", imageElement.src);
                    continue;
                }

                logger.info("UploadStatus for {} is {}", imageElement.src, localResult.isUploadSuccess());

                LJSinglePageElement element = new LJSinglePageElement(imageElement.src, LJSinglePageElementCategory.IMAGE);
                spResult.addElement(element);
            }

            bSuccessToReturn = true;
        }
        finally {
            //// tell the boss about the result
            boolean bComplete = ljSinglePageTaskClient.complete(spResult);
            if (!bComplete) {
                logger.error("Could not tell scheduler about the result");
            }
        }

        //// anyway, there is nothing we should do at this point.
        ////   maybe, there will be a mechanism that adds
        ////   several attempts.

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
