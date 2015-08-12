package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;

import java.util.Map;

/**
 * Created by nop on 8/10/15.
 */
public class DownloadAgents {
    /**
     * Agents.
     */
    private Map<Identity, DownloadAgent> agents;

    private IIdentityGenerator identityGenerator;

    public DownloadAgent getOrCreateAgentByAgentIdentity(Identity identity) {
        DownloadAgent agent = agents.get(identity);
        if (agent == null) {
            agent = new DownloadAgent(identity);
            agents.put(identity, agent);
        }

        return agent;
    }

    public DownloadAgent getAgentByDE(DownloadableElement downloadableElement) {
        for (DownloadAgent agent : agents.values()) {
            DownloadSequence sequence = agent.getActiveSequence();
            if (sequence == null)
                continue;

            if (sequence.isElementBeingDownloaded(downloadableElement))
                return agent;
        }

        return null;
    }
}
