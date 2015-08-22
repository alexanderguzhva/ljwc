package com.gschw.ljwc.lj.ljscheduler.client;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTask;
import com.gschw.ljwc.lj.ljscheduler.api.LJCalendarTaskResult;

import javax.ws.rs.client.Client;

/**
 * Created by nop on 8/15/15.
 */
public class LJCalendarTaskClient extends LJTaskClient<LJCalendarTask, LJCalendarTaskResult> implements ILJCalendarTaskClient{
    public LJCalendarTaskClient(Client client, LJTaskClientParameters parameters) {
        super(client, parameters);
    }

    @Override
    public LJCalendarTask acquireTask(Identity clientIdentity) {
        return super.acquireTask(clientIdentity, LJCalendarTask.class);
    }

    @Override
    public LJCalendarTaskResult download(LJCalendarTask task, long timeoutInMsec) {
        return super.download(task, timeoutInMsec, LJCalendarTaskResult.class);
    }

    @Override
    public LJCalendarTaskResult download(LJCalendarTask task) {
        return super.download(task, LJCalendarTaskResult.class);
    }
}
