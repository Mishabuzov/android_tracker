package com.example.tom.itistracker.models.network;

public class TaskStatusChangeObject {

    private int version;

    private int status;

    public TaskStatusChangeObject(int version, int status) {
        this.version = version;
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
