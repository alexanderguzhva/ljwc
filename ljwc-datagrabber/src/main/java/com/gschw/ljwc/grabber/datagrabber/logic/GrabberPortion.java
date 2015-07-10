package com.gschw.ljwc.grabber.datagrabber.logic;

import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberRequest;
import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberResult;

import com.gschw.ljwc.grabber.datagrabber.api.DataGrabberPullReply;

import java.util.Queue;
import java.util.Map;

import java.util.ArrayDeque;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by nop on 7/6/15.
 */
public class GrabberPortion {
    private Queue<DataGrabberRequest> requestsToProcess;

    private List<DataGrabberResult> results;

    //
    public GrabberPortion() {
        results = new ArrayList<>();
        requestsToProcess = new ArrayDeque<>();
    }

    public GrabberPortion(DataGrabberPullReply reply) {
        results = new ArrayList<>();
        requestsToProcess = new ArrayDeque<>();

        if (reply != null && reply.getRequests() != null)
            requestsToProcess.addAll(reply.getRequests());
    }

    //
    public boolean isEmpty() {
        return requestsToProcess.isEmpty();
    }

    //
    public DataGrabberRequest pop() {
        return requestsToProcess.poll();
    }

    //
    public void addResult(DataGrabberResult result) {
        results.add(result);
    }

    //
    public List<DataGrabberResult> getResults() {
        return results;
    }
}
