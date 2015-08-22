package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResultElement;
import com.gschw.ljwc.lj.ljscheduler.client.ILJCalendarTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * Created by nop on 8/15/15.
 */
public class Processor {

    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private IDGDownloadTaskClient downloadTaskClient;
    private ILJCalendarTaskClient calendarTaskClient;

    private ProcessorParameters parameters;

    public Processor(IDGDownloadTaskClient downloadTaskClient, ILJCalendarTaskClient calendarTaskClient, ProcessorParameters parameters) {
        this.downloadTaskClient = downloadTaskClient;
        this.calendarTaskClient = calendarTaskClient;
        this.parameters = parameters;
    }

    public boolean iterate() {
        logger.info("Let us iterate");
        LJCalendarTask task = calendarTaskClient.acquireTask(parameters.getProcessorIdentity());
        if (task == null) {
            logger.error("Could not get a task to process");
            return false;
        }

        String url = task.getUrl();
        if (url == null || url.isEmpty()) {
            logger.error("wtf, task url is null or empty");
            return false;
        }

        logger.info("Url is {}", url);

        //// now create session
        Identity sessionIdentity = downloadTaskClient.createSession();
        if (sessionIdentity == null) {
            logger.error("Could not create a session");
            return false;
        }

        logger.info("Created a session {}", sessionIdentity);

        try {
            LinksExtractor extractor = new LinksExtractor(downloadTaskClient, sessionIdentity, parameters.getUploadServiceUrl());
            CalendarAnalyzer calendarAnalyzer = new CalendarAnalyzer(extractor);

            ////
            HashSet<String> yearsURLs = new HashSet<>();
            calendarAnalyzer.getYearsFromCalendar(url, yearsURLs);
            for (String yearUrl : yearsURLs)
                logger.info("Found {} while parsing years", yearUrl);

            ////
            HashSet<String> monthURLs = new HashSet<>();
            calendarAnalyzer.getMonthsFromYears(yearsURLs, monthURLs);
            for (String monthUrl : monthURLs)
                logger.info("Found {} while parsing years and months", monthUrl);

            //// I want to do manual parsing month by month
            HashSet<String> postURLs = new HashSet<>();
            for (String monthUrl : monthURLs) {
                HashSet<String> oneMonth = new HashSet<>();
                oneMonth.add(monthUrl);

                HashSet<String> localPostURLs = new HashSet<>();
                calendarAnalyzer.getPostsFromMonths(oneMonth, localPostURLs);

                ////
                for (String postUrl : localPostURLs)
                    logger.info("Found {} while parsing {}", postUrl, monthUrl);

                postURLs.addAll(localPostURLs);
            }

            ////
            LJCalendarTaskResult result = new LJCalendarTaskResult(task.getTaskIdentity());
            for (String postUrl : postURLs) {
                LJCalendarTaskResultElement element =
                        new LJCalendarTaskResultElement(postUrl);
                result.addElement(element);
            }

            boolean bResult = calendarTaskClient.complete(result);
            if (!bResult) {
                logger.error("Could not complete {}", task.getTaskIdentity());
            }
        }
        finally {
            if (sessionIdentity != null) {
                boolean bResult = downloadTaskClient.deleteSession(sessionIdentity);
                if (!bResult) {
                    logger.error("Could not delete a session with id {}", sessionIdentity);
                }
            }
        }

        return true;
    }

}
