package com.gschw.ljwc.lj.dbwriter.resources;

import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberIdentity;
import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberPushRequest;
import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberPushReply;
import com.gschw.ljwc.lj.ljscheduler.api.NotificationPushRequest;
import com.gschw.ljwc.lj.ljscheduler.api.NotificationPushReply;
import com.gschw.ljwc.lj.ljscheduler.client.LJSchedulerClient;
import com.gschw.ljwc.storage.StorageElement;
import com.gschw.ljwc.storage.StorageElementCollection;
import com.gschw.ljwc.storage.client.StorageRESTClient;

/**
 * Created by nop on 7/6/15.
 */
public class WriterResource {
    private StorageRESTClient storageClient;

    private LJSchedulerClient ljSchedulerClient;

    @Path("{identityGuidString}")
    public DataGrabberPushReply doPost(@PathParam("identityGuidString") @NotNull String identityGuidString,
                       DataGrabberPushRequest pushRequest) {

        ////
        NotificationPushRequest notificationPushRequest = new NotificationPushRequest();
        StorageElementCollection storageElements = new StorageElementCollection();

        ////
        for (DataGrabberResult result : pushRequest.getRequests()) {
            if (result.isSuccess()) {
                notificationPushRequest.addSucceededElement(result.extractRequest());
                storageElements.
            } else {
                notificationPushRequest.addFailedElement(result.extractRequest());
            }
        }

        ////
        DataGrabberIdentity identity = new DataGrabberIdentity(identityGuidString);
        NotificationPushReply reply = ljSchedulerClient.pushNotification(identity, notificationPushRequest);

        if (reply.)
    }
}
