package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;

import java.util.Map;
import java.util.Set;

/**
 * Created by nop on 7/24/15.
 */
public class ElementsKeeper {
    private Map<Identity, DownloadableElement> downloadableElements;
    private Set<DownloadAgent> ljagents;

    private IIdentityGenerator identityGenerator;

    public ElementsKeeper(IIdentityGenerator identityGenerator) {
        this.identityGenerator = identityGenerator;
    }

    public void addFailure(DownloadAgent ljagent, DownloadableElement element) {
        ljagent.addFailure(element);
        element.addFailure(ljagent);
    }

    public void addSuccess(DownloadAgent ljagent, DownloadableElement element) {
        ljagent.addSuccess(element);
        element.addSuccess(ljagent);
    }

    public DownloadableElement getDownloadableElement(Identity deIdentity) {
        DownloadableElement de = downloadableElements.get(url);
        if (de == null) {

            //// create element
            Identity identity = identityGenerator.generate();
            LJDownloadElement element = new LJDownloadElement(identity, url);

            //// create and store de
            de = new DownloadableElement(element);
            downloadableElements.put(element, de);
        }

        return de;
    }
}
