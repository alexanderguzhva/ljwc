package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.IdentityRandomGenerator;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.client.ILJDownloadTaskClient;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageElement;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by nop on 7/24/15.
 */
public class DownloadSequence {
    private static Logger logger = LoggerFactory.getLogger(DownloadSequence.class);

    private DownloadStep step;

    private LJPostPageRoot rootPage;

    private Set<DownloadableElement> elementsBeingDownloaded;

    private ElementsKeeper elementsKeeper;

    //
    public LJPostPageRoot getRootPage() {
        return rootPage;
    }

    public void setRootPage(LJPostPageRoot rootPage) {
        this.rootPage = rootPage;
    }

    //
    public void reset() {
        elementsBeingDownloaded.clear();
        rootPage = null;
        step = DownloadStep.UNDEFINED;
    }

    //
    private LJDownloadTask acquireDEForRoot(LJAgent agent) {
        //// create de
        DownloadableElement downloadableElement =
                elementsKeeper.getDownloadableElement(rootPage.getUrl());

        //// store it
        elementsBeingDownloaded.clear();
        elementsBeingDownloaded.add(downloadableElement);

        //// add to task
        Identity taskIdentity = IdentityRandomGenerator.generate();
        LJDownloadTask task = new LJDownloadTask(taskIdentity);

        task.addElement(downloadableElement.getElement());

        return task;
    }

    private LJDownloadTask acquireDEForElements(LJAgent agent) {
        //// create task
        Identity taskIdentity = IdentityRandomGenerator.generate();
        LJDownloadTask task = new LJDownloadTask(taskIdentity);

        ////
        elementsBeingDownloaded.clear();

        //// add elements to task
        for (LJPostPageElement pageElement : rootPage.getElements()) {
            DownloadableElement downloadableElement =
                    elementsKeeper.getDownloadableElement(pageElement.getUrl());

            elementsBeingDownloaded.add(downloadableElement);

            task.addElement(downloadableElement.getElement());
        }

        return task;
    }


    public LJDownloadTask acquireDE(LJAgent agent) {
        switch(step) {
            case COMPLETE:
            case UNDEFINED:
                //// produce new root download
                LJDownloadTask deR = acquireDEForRoot(agent);

                step = DownloadStep.DOWNLOAD_ROOT;
                return deR;

            case DOWNLOADED_ROOT:
                //// produce new elements download
                LJDownloadTask deE = acquireDEForElements(agent);

                step = DownloadStep.DOWNLOAD_ElEMENTS;
                return deE;
        }

        logger.info("Cannot call acquireTask while step is {}", step);

        return null;
    }

    //
    private boolean completeElementRoot(DownloadableElement downloadableElement, boolean success) {
        boolean bRemoved = elementsBeingDownloaded.remove(downloadableElement);
        if (bRemoved) {
            //// could not remove
            logger.info("wtf, cannot remove element {}", downloadableElement.getElement().getUrl());
            return false;
        }

        //// ok, removed
        logger.info("Removed {}", downloadableElement.getElement().getUrl());

        ////
        step = DownloadStep.DOWNLOADED_ROOT;

        return true;
    }

    //
    private boolean completeElementElement(DownloadableElement downloadableElement, boolean success) {
        boolean bRemoved = elementsBeingDownloaded.remove(downloadableElement);
        if (bRemoved) {
            //// could not remove
            logger.info("wtf, cannot remove element {}", downloadableElement.getElement().getUrl());
            return false;
        }

        //// ok, removed
        logger.info("Removed {}", downloadableElement.getElement().getUrl());

        ////
        step = DownloadStep.COMPLETE;

        return true;
    }

    //
    public boolean completeElement(DownloadableElement downloadableElement, boolean success) {
        if (downloadableElement == null) {
            logger.info("null element!");
            return false;
        }

        switch(step) {
            case DOWNLOAD_ROOT:
                return completeElementRoot(downloadableElement, success);
            case DOWNLOAD_ElEMENTS:
                return completeElementElement(downloadableElement, success);
        }

        logger.info("wtf, my state is {}", step);
        return false;
    }

    //
    public boolean canAcquireNewTask() {
        return (step == DownloadStep.COMPLETE ||
                step == DownloadStep.UNDEFINED);
    }
}
