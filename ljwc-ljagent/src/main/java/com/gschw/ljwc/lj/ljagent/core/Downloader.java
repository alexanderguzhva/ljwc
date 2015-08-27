package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadResult;
import com.gschw.ljwc.grabber.datagrabber.api.DGDownloadTask;
import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import com.gschw.ljwc.lj.ljreader.client.SimpleDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 8/26/15.
 */
public class Downloader {
    private static Logger logger = LoggerFactory.getLogger(Downloader.class);

    private Identity dgClientSession;


    private IDGDownloadTaskClient dgDownloadClient;
    private SimpleDownloader simpleDownloader;

    public Downloader(IDGDownloadTaskClient dgDownloadClient, SimpleDownloader simpleDownloader) {
        this.dgDownloadClient = dgDownloadClient;
        this.simpleDownloader = simpleDownloader;
    }

    public boolean download(DGDownloadTask downloadTask) {
        //// check whether the file was downloaded
        boolean isFileDownloaded = simpleDownloader.check(downloadTask.getUrl());
        logger.info("{} downloaded status: {}", downloadTask.getUrl(), isFileDownloaded);

        if (isFileDownloaded)
            return true;

        ////
        createIdentity();

        //// step 1
        DGDownloadResult result = dgDownloadClient.download(dgClientSession, downloadTask);
        if (result == null) {
            logger.info("Could not download {}", downloadTask.getUrl());
            return false;
        }

        return result.isUploadSuccess();
    }

    private boolean createIdentity() {
        if (dgClientSession != null)
            return true;

        dgClientSession = dgDownloadClient.createSession();
        if (dgClientSession == null) {
            logger.error("Could not create a session");
            return false;
        }

        return true;
    }

    public boolean close() {
        if (dgClientSession != null) {
            boolean result = dgDownloadClient.deleteSession(dgClientSession);
            if (!result) {
                logger.error("Could not delete a session");
                return false;
            }

            dgClientSession = null;
            return true;
        }

        return true;
    }
}
