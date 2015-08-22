package com.gschw.ljwc.lj.ljcalendaragent.calendar;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.StandardIdentityRandomGenerator;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created by nop on 8/12/15.
 */
public class LinksExtractor {
    private static Logger logger = LoggerFactory.getLogger(LinksExtractor.class);

    private IDGDownloadTaskClient downloadTaskClient;
    private Identity sessionIdentity;

    private IIdentityGenerator identityGenerator;

    public LinksExtractor(IDGDownloadTaskClient downloadTaskClient, Identity sessionIdentity) {
        this.downloadTaskClient = downloadTaskClient;
        this.sessionIdentity = sessionIdentity;

        this.identityGenerator = new StandardIdentityRandomGenerator();
    }

    public void ExtractLinks(String baseURL, Pattern pattern, Collection<String> outputURLs) {
        ////
        DGDownloadTask task = new DGDownloadTask();
        task.setTaskIdentity(identityGenerator.generate());
        task.setUrl(baseURL);
        task.setReturnDataInReply(true);
        task.setUploadDataToBase(true);

        DGDownloadResult result = downloadTaskClient.download(sessionIdentity, task);
        if (result == null) {
            logger.info("Could not download {}", baseURL);
            return;
        }
        if (result.getData() == null) {
            logger.warn("wtf, returned data is null");
            return;
        }

        try {
            Document document;
            try (ByteArrayInputStream bais = new ByteArrayInputStream(result.getData())) {
                document = Jsoup.parse(bais, "UTF-8", baseURL);

                ////
                LinksMatcher.MatchLinks(document, pattern, outputURLs);
            }
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }

        logger.info("Processed ExtractLinks from {}", baseURL);
    }


}
