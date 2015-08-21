package com.gschw.ljwc.lj.ljscheduler.pscheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResultElement;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageElement;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hadoop on 8/21/15.
 */
public class SinglePageKeeper extends Keeper<LJSinglePageTask, LJSinglePageTaskResult, SinglePageAgent> {
    private static Logger logger = LoggerFactory.getLogger(SinglePageKeeper.class);

    @Override
    protected SinglePageAgent createAgent(Identity clientIdentity) {
        return new SinglePageAgent(clientIdentity);
    }

    @Override
    protected void processCompletedElement(LJSinglePageTaskResult result) {
        for (LJSinglePageElement element : result.getElements()) {
            logger.info("Got {}, {} for {}", element.getUrl(), element.getCategory(), result.getTaskIdentity());
        }
    }
}
