package com.example.tom.itistracker.models.network.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetails {

    private int id;

    @JsonProperty("task_statuses")
    private List<TaskStatusInProject> taskStatuses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TaskStatusInProject> getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(List<TaskStatusInProject> taskStatuses) {
        this.taskStatuses = taskStatuses;
    }
}
