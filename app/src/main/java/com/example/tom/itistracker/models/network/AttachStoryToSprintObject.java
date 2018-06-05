package com.example.tom.itistracker.models.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

public class AttachStoryToSprintObject {

    private int version;

    @Nullable
    @JsonProperty("milestone")
    private Long sprintId;

    public AttachStoryToSprintObject(final int version,
                                     @Nullable final Long sprintId) {
        this.version = version;
        this.sprintId = sprintId;
    }

    public AttachStoryToSprintObject() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Nullable
    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(@Nullable final Long sprintId) {
        this.sprintId = sprintId;
    }
}
