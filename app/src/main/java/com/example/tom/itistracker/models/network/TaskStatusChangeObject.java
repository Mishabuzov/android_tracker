package com.example.tom.itistracker.models.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskStatusChangeObject {

    private long status;

    private int version;

    public TaskStatusChangeObject(long status, int version) {
        this.status = status;
        this.version = version;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
