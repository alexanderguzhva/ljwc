package com.gschw.ljwc.lj.executor.core;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.StandardIdentityRandomGenerator;
import com.gschw.ljwc.lj.ljscheduler.api.*;
import com.gschw.ljwc.lj.ljscheduler.client.LJCalendarTaskClient;
import com.gschw.ljwc.lj.ljscheduler.client.LJSinglePageTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hadoop on 8/21/15.
 */
public class X {
    private static Logger logger = LoggerFactory.getLogger(X.class);

    private LJCalendarTaskClient calendarTaskClient;
    private LJSinglePageTaskClient singlePageTaskClient;

    private IIdentityGenerator identityGenerator;

    public X(LJCalendarTaskClient calendarTaskClient, LJSinglePageTaskClient singlePageTaskClient) {
        this.calendarTaskClient = calendarTaskClient;
        this.singlePageTaskClient = singlePageTaskClient;

        this.identityGenerator = new StandardIdentityRandomGenerator();
    }

    public void downloadAll(String rootPage) {
        logger.info("running full {}", rootPage);

        Identity identity = identityGenerator.generate();
        LJCalendarTask task = new LJCalendarTask(identity, rootPage);
        LJCalendarTaskResult result = calendarTaskClient.download(task);
        if (result == null) {
            logger.info("Could not process {}", rootPage);
            return;
        }

        for (LJCalendarTaskResultElement element : result.getElements()) {
            logger.info("Found {} in {}", element.getUrl(), rootPage);
        }

        //// going one by one
        for (LJCalendarTaskResultElement element : result.getElements()) {
            logger.info("Processing {}", element.getUrl());

            Identity spIdentity = identityGenerator.generate();
            LJSinglePageTask spTask = new LJSinglePageTask(spIdentity, element.getUrl());
            LJSinglePageTaskResult spResult = singlePageTaskClient.download(spTask);
            if (spResult == null) {
                logger.info("Could not process {}", element.getUrl());
                continue;
            }

            for (LJSinglePageElement spElement : spResult.getElements()) {
                logger.info("Downloaded {}, {}", spElement.getUrl(), spElement.getCategory().toStringValue());
            }
        }


    }


    public void downloadSinglePage(String singlePage) {

    }
}
