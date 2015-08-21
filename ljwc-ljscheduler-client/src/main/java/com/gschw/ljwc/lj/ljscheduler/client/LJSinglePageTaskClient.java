package com.gschw.ljwc.lj.ljscheduler.client;

import com.google.common.base.Throwables;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJSinglePageTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hadoop on 8/21/15.
 */
public class LJSinglePageTaskClient extends LJTaskClient<LJSinglePageTask, LJSinglePageTaskResult> implements ILJSinglePageTaskClient {
    public LJSinglePageTaskClient(Client client, LJTaskClientParameters parameters) {
        super(client, parameters);
    }

    @Override
    public LJSinglePageTask acquireTask(Identity clientIdentity) {
        return super.acquireTask(clientIdentity, LJSinglePageTask.class);
    }

    @Override
    public LJSinglePageTaskResult download(LJSinglePageTask task, long timeoutInMsec) {
        return super.download(task, timeoutInMsec, LJSinglePageTaskResult.class);
    }

    @Override
    public LJSinglePageTaskResult download(LJSinglePageTask task) {
        return super.download(task, LJSinglePageTaskResult.class);
    }
}
