package com.example.tom.itistracker.models.network;

import android.support.annotation.IdRes;

import com.example.tom.itistracker.R;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TaskStatus.TaskStatusDeserializer.class)
public enum TaskStatus {

    NEW(11, R.id.new_board),
    IN_PROGRESS(12, R.id.in_progress_board),
    READY_FOR_TEST(13, R.id.test_board),
    CLOSED(14, R.id.closed_board),
    BLOCKED(15, R.id.blocked_board);

    private final int status;

    @IdRes
    private final int boardId;

    TaskStatus(final int status,
               @IdRes final int boardId) {
        this.status = status;
        this.boardId = boardId;
    }

    public int getStatus() {
        return status;
    }

    public static int getStatusByBoardId(@IdRes final int boardId) throws IOException {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getBoardId() == boardId) {
                return taskStatus.getStatus();
            }
        }
        throw new IOException(String.format("TaskStatus: unexpected board id: %1$d", boardId));
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
            int status = jsonNode.intValue();

            for (TaskStatus taskStatus : TaskStatus.values()) {
                if (taskStatus.getStatus() == status) {
                    return taskStatus;
                }
            }
            throw dc.instantiationException(TaskStatus.class, "Cannot deserialize TaskStatus from status " + status);
        }
    }

}
