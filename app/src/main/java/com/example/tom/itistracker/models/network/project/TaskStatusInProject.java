package com.example.tom.itistracker.models.network.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskStatusInProject {

    private long id;

    private String name;

    @JsonProperty("is_closed")
    private boolean isClosed;

    private int project;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }
}
