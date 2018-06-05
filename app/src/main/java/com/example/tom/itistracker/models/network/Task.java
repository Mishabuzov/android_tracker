package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task implements Parcelable {

    private long id;

    @JsonProperty("subject")
    private String title;

    @JsonProperty("assigned_to")
    private long executorId;

    @JsonProperty("assigned_to_extra_info")
    private User executor;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("finished_date")
    private String deadline;

    @JsonProperty("is_blocked")
    private boolean isBlocked;

    @JsonProperty("is_closed")
    private boolean isClosed;

    @JsonProperty("user_story_extra_info")
    private UserStoryExtra userStoryExtra;

    private int version;

    private TaskStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public UserStoryExtra getUserStoryExtra() {
        return userStoryExtra;
    }

    public void setUserStoryExtra(UserStoryExtra userStoryExtra) {
        this.userStoryExtra = userStoryExtra;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeLong(this.executorId);
        dest.writeParcelable(this.executor, flags);
        dest.writeString(this.createdDate);
        dest.writeString(this.deadline);
        dest.writeByte(this.isBlocked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isClosed ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.userStoryExtra, flags);
        dest.writeInt(this.version);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    public Task() {
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.executorId = in.readLong();
        this.executor = in.readParcelable(User.class.getClassLoader());
        this.createdDate = in.readString();
        this.deadline = in.readString();
        this.isBlocked = in.readByte() != 0;
        this.isClosed = in.readByte() != 0;
        this.userStoryExtra = in.readParcelable(UserStoryExtra.class.getClassLoader());
        this.version = in.readInt();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : TaskStatus.values()[tmpStatus];
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
