package com.example.tom.itistracker.models.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStoryStatus {

    NEW("New"),

    READY("Ready"),

    IN_PROGRESS("In progress"),

    READY_FOR_TEST("Ready for test"),

    DONE("Done"),

    ARCHIVED("Archived");

    @JsonProperty("name")
    private String status;

    UserStoryStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
