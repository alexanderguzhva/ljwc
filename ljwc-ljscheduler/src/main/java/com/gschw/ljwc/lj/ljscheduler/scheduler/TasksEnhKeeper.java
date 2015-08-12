package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.SequentialIdentityGenerator;
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

    /**
     * Elements that are being downloaded right now.
     */
    private DownloadSequences sequences;

    /**
     * A collection of root pages that need to be processed.
     */
    private Queue<LJPostPageRoot> rootPagesToProcess;

    /**
     * Agents.
     */
    private DownloadAgents agents;


    //
    private Map<Identity, DownloadableElement> elements;


    private IIdentityGenerator sequenceIdentityGenerator;


    public TasksEnhKeeper(TasksEnhKeeperParameters parameters) {
        this.parameters = parameters;

        agents = new DownloadAgents();

        sequenceIdentityGenerator = new SequentialIdentityGenerator();
    }

    //
    @Override
    public LJDownloadTask acquireTask(Identity clientIdentity) {
        if (rootPagesToProcess.isEmpty())
            return null;

        ////
        DownloadAgent agent = agents.getAgent(clientIdentity);
        if (agent == null) {
            logger.error("Could not get agent");
            return null;
        }

        //// active sequence
        DownloadSequence sequence = agent.getActiveSequence();
        if (sequence == null) {
            LJPostPageRoot pageRoot = rootPagesToProcess.poll();

            //// create a new sequence
            sequence = sequences.getOrCreateSequence(pageRoot);
            if (!sequence.isComplete()) {
                logger.error("wtf, cannot acquire a new task for a newly created sequence");
                return null;
            }
        }

        LJDownloadTask task = sequence.acquireDE(agent);
        return task;
    }

    @Override
    public boolean completeElement(Identity elementIdentity, boolean success) {
        //// find an element
        DownloadableElement downloadableElement = elements.get(elementIdentity);
        if (downloadableElement == null) {
            logger.error("provided element {} is not known", elementIdentity);
            return false;
        }

        //// find corresponding sequence
        DownloadAgent agent = agents.getAgentByDE(downloadableElement);
        if (agent == null) {
            logger.error("agent is not found");
            return false;
        }

        DownloadSequence sequence = agent.getActiveSequence();

        //// complete it
        boolean result = sequence.completeElement(downloadableElement, success);
        if (result) {
            //// operation is complete
            if (sequence.isComplete()) {
                //// task is complete
                logger.info("{} is complete", sequence.getRootPage().getUrl());

                agent.setActiveSequence(null);
            }
            else {
                //// do nothing
                logger.info("downloaded an element {} for {}",
                        downloadableElement.getElement().getUrl(),
                        sequence.getRootPage().getUrl());
            }

            return true;
        }

        return result;
    }
}
