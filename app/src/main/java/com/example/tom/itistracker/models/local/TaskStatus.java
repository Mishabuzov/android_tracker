package com.example.tom.itistracker.models.local;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.example.tom.itistracker.R;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TaskStatus.TaskStatusDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public enum TaskStatus {

    NEW("New", R.id.new_board),
    IN_PROGRESS("In progress", R.id.in_progress_board),
    READY_FOR_TEST("Ready for test", R.id.test_board),
    CLOSED("Closed", R.id.closed_board),
    BLOCKED("Needs Info", R.id.blocked_board);

    private final String name;
    @IdRes
    private final int boardId;

    TaskStatus(@NonNull final String name,
               @IdRes final int boardId) {
        this.name = name;
        this.boardId = boardId;
    }

    @NonNull
    public static TaskStatus getStatusByBoardId(@IdRes final int boardId) throws IOException {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getBoardId() == boardId) {
                return taskStatus;
            }
        }
        throw new IOException(String.format("TaskStatus: unexpected board id: %1$d", boardId));
    }

    @NonNull
    public String getName() {
        return name;
    }

    @IdRes
    public int getBoardId() {
        return boardId;
    }

    public static class TaskStatusDeserializer extends StdDeserializer<TaskStatus> {
        public TaskStatusDeserializer() {
            super(TaskStatus.class);
        }

        @Override
        public TaskStatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
            final JsonNode jsonNode = jp.readValueAsTree();
            final String currentTaskStatus = jsonNode.get("name").textValue();

            for (TaskStatus taskStatus : TaskStatus.values()) {
                if (taskStatus.getName().equals(currentTaskStatus)) {
                    return taskStatus;
                }
            }

            throw dc.instantiationException(TaskStatus.class,
                    "Cannot deserialize TaskStatus from name " + currentTaskStatus);
        }
    }

}
