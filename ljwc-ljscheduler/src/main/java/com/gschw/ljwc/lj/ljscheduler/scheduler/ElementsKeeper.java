package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.IdentityRandomGenerator;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadElement;

import java.util.Map;
import java.util.Set;

/**
 * Created by nop on 7/24/15.
 */
public class ElementsKeeper {
    private Map<String, DownloadableElement> downloadableElements;
    private Set<LJAgent> ljagents;

    public void addFailure(LJAgent ljagent, DownloadableElement element) {
        ljagent.addFailure(element);
        element.addFailure(ljagent);
    }

    public void addSuccess(LJAgent ljagent, DownloadableElement element) {
        ljagent.addSuccess(element);
        element.addSuccess(ljagent);
    }

    public DownloadableElement getDownloadableElement(String url) {
        return null;
        /* TODO later
        DownloadableElement de = downloadableElements.get(url);
        if (de == null) {

            //// create element
            Identity identity = IdentityRandomGenerator.generate();
            LJDownloadElement element = new LJDownloadElement(identity, url);

            //// create and store de
            de = new DownloadableElement(element);
            downloadableElements.put(element, de);
        }

        return de;
        */
    }
}
