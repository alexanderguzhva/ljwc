package com.gschw.ljwc.lj.ljagent.core;

import com.gschw.ljwc.grabber.datagrabber.client.IDGDownloadTaskClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nop on 7/15/15.
 * This is an active element that periodically pulls tasks from
 *   ljscheduler and coordinates datagrabbers to download them.
 */
public class Processor {
    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    private IDGDownloadTaskClient dgDownloadClient;

    private ProcessorParameters parameters;

    // note: this procedre should be able to process errors from
    //  datagrabbers (as they may go down or limit the number
    //  of cncurrent HTTP connections.
    private void process() {

    }

}
