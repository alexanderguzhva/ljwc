package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.core.LJPostPageRoot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nop on 8/10/15.
 */
public class DownloadSequences {
    /**
     * Elements that are being downloaded right now.
     */
    private Map<Identity, DownloadSequence> sequences;

    private IIdentityGenerator identityGenerator;

    public DownloadSequences(IIdentityGenerator identityGenerator) {
        this.identityGenerator = identityGenerator;

        sequences = new HashMap<>();
    }

    /**
     * Gets or creates a sequence for a page root.
     * @param pageRoot PageRoot
     * @return Sequence for the given pageRoot.
     */
    public DownloadSequence getOrCreateSequence(LJPostPageRoot pageRoot) {
        DownloadSequence sequence = sequences.get(pageRoot.getIdentity());
        if (sequence == null) {
            sequence = new DownloadSequence(pageRoot, identityGenerator);
            sequences.put(pageRoot.getIdentity(), sequence);
        }

        return sequence;
    }

    public DownloadSequence getSequenceBySequenceIdentity(Identity sequenceIdentity) {
        return sequences.get(sequenceIdentity);
    }

}
