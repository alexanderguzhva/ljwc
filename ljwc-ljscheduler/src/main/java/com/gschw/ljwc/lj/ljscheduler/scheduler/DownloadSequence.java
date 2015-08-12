package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageElement;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by nop on 7/24/15.
 *
 * Class holds the logic for downloading a page root and its subelements.
 */
public class DownloadSequence {
    /**
     * A logger.
     */
    private static Logger logger = LoggerFactory.getLogger(DownloadSequence.class);

    /**
     * Current step in processing.
     */
    private DownloadStep step;

    /**
     * A root page.
     */
    private LJPostPageRoot rootPage;

    /**
     * DownloadableElements that are being downloaded right now.
     */
    private Set<DownloadableElement> elementsBeingDownloaded;

    //
    public LJPostPageRoot getRootPage() {
        return rootPage;
    }

    public void setRootPage(LJPostPageRoot rootPage) {
        this.rootPage = rootPage;
    }


    /**
     * An identity generator for new tasks.
     */
    private IIdentityGenerator taskIdentityGenerator;


    //
    public DownloadSequence(LJPostPageRoot rootPage, IIdentityGenerator taskIdentityGenerator) {
        this.rootPage = rootPage;
        this.taskIdentityGenerator = taskIdentityGenerator;

        ////
        internalReset();
    }

    //
    private void internalReset() {
        elementsBeingDownloaded.clear();
        rootPage = null;
        step = DownloadStep.UNDEFINED;
    }

    //
    public void reset() {
        internalReset();
    }

    /**
     * Creates a task for downloading the current root page HTML for a given downloadAgent.
     * Basically, this task will contain only 1 element to be downloaded.
     * @param downloadAgent DownloadAgent
     * @return A task for the given downloadAgent.
     */
    private LJDownloadTask acquireDEForRoot(DownloadAgent downloadAgent) {
        //// create de
        DownloadableElement downloadableElement =
                new DownloadableElement(rootPage.createLJDownloadElement());

        //// store it
        elementsBeingDownloaded.clear();
        elementsBeingDownloaded.add(downloadableElement);

        //// add to task
        Identity taskIdentity = taskIdentityGenerator.generate();
        LJDownloadTask task = new LJDownloadTask(taskIdentity);

        task.addElement(downloadableElement.getElement());

        return task;
    }

    /**
     * Creates a task for downloading elements of the current root page HTML for a given downloadAgent.
     * This task contains all subelements of the current root page.
     * @param downloadAgent DownloadAgent
     * @return A task for the given downloadAgent.
     */
    private LJDownloadTask acquireDEForElements(DownloadAgent downloadAgent) {
        //// create task
        Identity taskIdentity = taskIdentityGenerator.generate();
        LJDownloadTask task = new LJDownloadTask(taskIdentity);

        ////
        elementsBeingDownloaded.clear();

        //// add elements to task
        for (LJPostPageElement pageElement : rootPage.getElements()) {
            DownloadableElement downloadableElement =
                    new DownloadableElement(pageElement.createLJDownloadElement());

            elementsBeingDownloaded.add(downloadableElement);

            task.addElement(downloadableElement.getElement());
        }

        return task;
    }


    public LJDownloadTask acquireDE(DownloadAgent downloadAgent) {
        switch(step) {
            case COMPLETE:
            case UNDEFINED:
                //// produce new root download
                LJDownloadTask deR = acquireDEForRoot(downloadAgent);

                step = DownloadStep.DOWNLOAD_ROOT;
                return deR;

            case DOWNLOADED_ROOT:
                //// produce new elements download
                LJDownloadTask deE = acquireDEForElements(downloadAgent);

                step = DownloadStep.DOWNLOAD_ELEMENTS;
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
            case DOWNLOAD_ELEMENTS:
                return completeElementElement(downloadableElement, success);
        }

        logger.info("wtf, my state is {}", step);
        return false;
    }

    //
    public boolean isComplete() {
        return (step == DownloadStep.COMPLETE ||
                step == DownloadStep.UNDEFINED);
    }

    //
    public boolean isElementBeingDownloaded(DownloadableElement element) {
        return elementsBeingDownloaded.contains(element);
    }
}
