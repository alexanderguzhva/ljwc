package com.gschw.ljwc.lj.ljscheduler;

import com.gschw.ljwc.lj.ljscheduler.scheduler.TasksKeeperParameters;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by nop on 7/6/15.
 */
public class DWConfiguration extends Configuration {
    private TasksKeeperParameters tasksKeeperParameters;

    @JsonProperty
    public TasksKeeperParameters getTasksKeeperParameters() {
        return tasksKeeperParameters;
    }

    @JsonProperty
    public void setTasksKeeperParameters(TasksKeeperParameters tasksKeeperParameters) {
        this.tasksKeeperParameters = tasksKeeperParameters;
    }
}
