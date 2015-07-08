package com.gschw.ljwc.grabber.datagrabber.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gschw.ljwc.grabber.datagrabber.core.Grabber;
import com.gschw.ljwc.grabber.datagrabber.core.GrabberResult;

import com.gschw.ljwc.lj.ljscheduler.client.LJSchedulerClient;
import com.gschw.ljwc.grabber.datagrabber.api.*;

import java.util.UUID;

/**
 * Created by nop on 7/6/15.
 */
public class GrabberLogic {
    private static Logger logger = LoggerFactory.getLogger(GrabberLogic.class);

    private GrabberLogicParameters parameters;

    private Grabber grabber;

    private LJSchedulerClient schedulerClient;

    private GrabberState state = GrabberState.UNINITIALIZED;

    //
    public GrabberLogic(GrabberLogicParameters parameters, Grabber grabber, LJSchedulerClient schedulerClient) {
        this.parameters = parameters;
        this.grabber = grabber;
        this.schedulerClient = schedulerClient;
    }

    //
    private GrabberPortion currentPortion;
    private DataGrabberIdentity myIdentity;

    //
    private void initialize() {
        currentPortion = null;

        ////
        initializeIdentity();
    }

    //
    private void initializeIdentity() {
        myIdentity = new DataGrabberIdentity(UUID.randomUUID().toString());
    }

    //
    private void pullRequest() {
        DataGrabberPullRequest pullRequest = new DataGrabberPullRequest();
        DataGrabberPullReply pullReply = schedulerClient.pullRequest(myIdentity, pullRequest);

        currentPortion = new GrabberPortion(pullReply);
    }

    //
    private void processDataGrabberRequest(DataGrabberRequest request) {
        String url = request.getUrl();

        GrabberResult result = grabber.grab(url);
        if (result == null) {
            //// failed
            currentPortion.addResult(DataGrabberResult.createFailed(url));
            return;
        }

        //// success
        currentPortion.addResult(DataGrabberResult.createSuccess(url, result.getData()));
    }

    //
    private void stepInitialize() {
        initialize();

        ////
        state = GrabberState.SHOULD_PULL_A_REQUEST;
    }

    //
    private void stepSleeping() {
        //// do sleep here
        try {
            Thread.sleep(parameters.getSleepingTime());
        } catch(InterruptedException e) {
            state = GrabberState.NEED_TO_TERMINATE;
            return;
        }

        ////
        state = GrabberState.SHOULD_PULL_A_REQUEST;
    }

    //
    private void stepPullARequest() {
        if (currentPortion.isEmpty()) {

            //// queue is empty
            pullRequest();

            if (currentPortion.isEmpty()) {
                //// should not pull anything
                state = GrabberState.SLEEPING;
                return;
            }
        }

        state = GrabberState.SHOULD_GRAB_AN_URL;
    }

    //
    private void stepGrabAnURL() {
        DataGrabberRequest request = currentPortion.pop();
        processDataGrabberRequest(request);

        if (currentPortion.getResults().size() >= parameters.getAccumulateResultsLimit()) {
            state = GrabberState.SHOULD_PUSH_A_REPLY;
        }
        else {
            state = GrabberState.SHOULD_GRAB_AN_URL;
        }
    }

    //
    private void stepPushAReply() {
    }

    //
    private void step() {
        switch(state) {
            case UNINITIALIZED:
                stepInitialize();
                break;

            case SHOULD_GRAB_AN_URL:
                stepGrabAnURL();
                break;

            case SHOULD_PULL_A_REQUEST:
                stepPullARequest();
                break;

            case SHOULD_PUSH_A_REPLY:
                stepPushAReply();
                break;

            case SLEEPING:
                stepSleeping();
                break;

            case NEED_TO_TERMINATE:
                break;
        }
    }
}
