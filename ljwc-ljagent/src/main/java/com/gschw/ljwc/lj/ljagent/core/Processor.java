package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.html.htmlparser.api.ElementsCollection;
import com.gschw.ljwc.html.htmlparser.api.HTMLParseResultByDBURL;
import com.gschw.ljwc.html.htmlparser.api.ImageElement;
import com.gschw.ljwc.html.htmlparser.client.IHTMLParserClient;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
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

    private SimpleDownloader simpleDownloader;

    private ProcessorParameters parameters;


    public Processor(
            IDGDownloadTaskClient dgDownloadClient,
            ILJSinglePageTaskClient ljSinglePageTaskClient,
            IHTMLParserClient htmlParserClient,
            SimpleDownloader simpleDownloader,
            ProcessorParameters parameters) {
        this.dgDownloadClient = dgDownloadClient;
        this.ljSinglePageTaskClient = ljSinglePageTaskClient;
        this.htmlParserClient = htmlParserClient;
        this.simpleDownloader = simpleDownloader;
        this.parameters = parameters;
    }


    //
    public boolean iterate() {
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

        ////
        Downloader downloader = new Downloader(dgDownloadClient, simpleDownloader);
        try {
            //// so, an order of operations is the following:
            //// 1. download file and place it in db
            //// 2. ask htmlparser to parse a file and return a list of links to be downloaded
            //// 3. download links and store them in db
            LJSinglePageTaskResult spResult = new LJSinglePageTaskResult(task.getTaskIdentity());

            try {
                //// let this single grabber to process it
                DGDownloadTask downloadTask = new DGDownloadTask();
                downloadTask.setTaskIdentity(task.getTaskIdentity());
                downloadTask.setUrl(task.getUrl());
                downloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
                downloadTask.setReturnDataInReply(false);
                downloadTask.setUploadDataToBase(true);

                ////
                boolean result = downloader.download(downloadTask);

                ////
                if (!result) {
                    //// could not obtain base file
                    return false;
                }

                //// add to result
                LJSinglePageElement elementPage = new LJSinglePageElement(task.getUrl(), LJSinglePageElementCategory.PAGE);
                spResult.addElement(elementPage);

                //// ok, step 2
                ElementsCollection parsedElements = htmlParserClient.parse(task.getUrl());
                if (parsedElements == null) {
                    logger.info("Could not parse elements for {}", task.getUrl());
                    return true;
                }

                for (ImageElement imageElement : parsedElements.getImages()) {
                    DGDownloadTask elementDownloadTask = new DGDownloadTask();
                    elementDownloadTask.setTaskIdentity(task.getTaskIdentity());
                    elementDownloadTask.setUrl(imageElement.src);
                    elementDownloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
                    elementDownloadTask.setReturnDataInReply(false);
                    elementDownloadTask.setUploadDataToBase(true);

                    boolean bDownloadElement = downloader.download(elementDownloadTask);
                    if (!bDownloadElement) {
                        logger.info("Could not get {}", elementDownloadTask.getUrl());
                        continue;
                    }

                    LJSinglePageElement element = new LJSinglePageElement(elementDownloadTask.getUrl(), LJSinglePageElementCategory.IMAGE);
                    spResult.addElement(element);
                }

            } finally {
                //// tell the boss about the result
                boolean bComplete = ljSinglePageTaskClient.complete(spResult);
                if (!bComplete) {
                    logger.error("Could not tell scheduler about the result");
                }
            }
        } finally {
            downloader.close();
        }

        return true;
    }

//    //
//    public boolean iterate() {
//        //// grab a task
//        LJSinglePageTask task = ljSinglePageTaskClient.acquireTask(parameters.getProcessorIdentity());
//        if (task == null) {
//            logger.info("Could not get a task to process");
//            return false;
//        }
//
//        if (!parameters.getProcessorIdentity().equals(task.getAssignedTo())) {
//            logger.error("wtf, I am {} and task identity is {}", parameters.getProcessorIdentity(), task.getAssignedTo());
//            return false;
//        }
//
//        //// check whether the file was downloaded
//        boolean isFileDownloaded = simpleDownloader.check(task.getUrl());
//        logger.info("{} downloaded status: {}", task.getUrl(), isFileDownloaded);
//
//
//        ////
//        Identity dgClientSession = null;
//
//
//        Identity dgClientSession = dgDownloadClient.createSession();
//        if (dgClientSession == null) {
//            logger.error("Could not create a session");
//            return false;
//        }
//
//        try {
//            //// so, an order of operations is the following:
//            //// 1. download file and place it in db
//            //// 2. ask htmlparser to parse a file and return a list of links to be downloaded
//            //// 3. download links and store them in db
//            LJSinglePageTaskResult spResult = new LJSinglePageTaskResult(task.getTaskIdentity());
//
//
//
//
//            //// let this single grabber to process it
//            DGDownloadTask downloadTask = new DGDownloadTask();
//            downloadTask.setTaskIdentity(task.getTaskIdentity());
//            downloadTask.setUrl(task.getUrl());
//            downloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
//            downloadTask.setReturnDataInReply(false);
//            downloadTask.setUploadDataToBase(true);
//
//            //// step 1
//            DGDownloadResult result = dgDownloadClient.download(dgClientSession, downloadTask);
//
//            boolean bSuccessToReturn = false;
//            try {
//                if (result == null) {
//                    logger.info("Could not download {}", task.getUrl());
//                    return false;
//                }
//
//                //// add to result
//                LJSinglePageElement elementPage = new LJSinglePageElement(task.getUrl(), LJSinglePageElementCategory.PAGE);
//                spResult.addElement(elementPage);
//
//                //// ok, step 2
//                ElementsCollection parsedElements = htmlParserClient.parse(task.getUrl());
//                if (parsedElements == null) {
//                    logger.info("Could not parse elements for {}", task.getUrl());
//                    return false;
//                }
//
//                //// download images
//                for (ImageElement imageElement : parsedElements.getImages()) {
//                    DGDownloadTask elementDownloadTask = new DGDownloadTask();
//                    elementDownloadTask.setTaskIdentity(task.getTaskIdentity());
//                    elementDownloadTask.setUrl(imageElement.src);
//                    elementDownloadTask.setUploadServiceURL(parameters.getUploaderServiceUrl());
//                    elementDownloadTask.setReturnDataInReply(false);
//                    elementDownloadTask.setUploadDataToBase(true);
//
//                    DGDownloadResult localResult = dgDownloadClient.download(dgClientSession, elementDownloadTask);
//                    if (localResult == null) {
//                        logger.info("Could not download {}", imageElement.src);
//                        continue;
//                    }
//
//                    logger.info("UploadStatus for {} is {}", imageElement.src, localResult.isUploadSuccess());
//
//                    LJSinglePageElement element = new LJSinglePageElement(imageElement.src, LJSinglePageElementCategory.IMAGE);
//                    spResult.addElement(element);
//                }
//
//                bSuccessToReturn = true;
//            } finally {
//                //// tell the boss about the result
//                boolean bComplete = ljSinglePageTaskClient.complete(spResult);
//                if (!bComplete) {
//                    logger.error("Could not tell scheduler about the result");
//                }
//            }
//        } finally {
//            if (dgClientSession != null) {
//                boolean result = dgDownloadClient.deleteSession(dgClientSession);
//                if (!result) {
//                    logger.error("Could not delete a session");
//                    return false;
//                }
//            }
//        }
//
//
//        //// anyway, there is nothing we should do at this point.
//        ////   maybe, there will be a mechanism that adds
//        ////   several attempts.
//
//        return true;
//    }

}
