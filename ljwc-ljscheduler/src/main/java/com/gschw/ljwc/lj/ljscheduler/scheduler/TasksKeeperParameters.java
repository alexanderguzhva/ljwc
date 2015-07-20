package com.gschw.ljwc.lj.ljscheduler.scheduler;

import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.lj.ljscheduler.api.LJDownloadTask;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nop on 7/19/15.
 */
public class TasksKeeperParameters {
    private List<LJDownloadTask> tasksToProcess;

    public TasksKeeperParameters() {
        this.tasksToProcess = new ArrayList<>();
    }

    @JsonProperty("tasksToProcess")
    public List<LJDownloadTask> getTasksToProcess() {
        return tasksToProcess;
    }

}
