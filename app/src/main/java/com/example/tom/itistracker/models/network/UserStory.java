package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStory implements Parcelable {

    private long id;

    @JsonProperty("assigned_to_extra_info")
    private User executor;

    @JsonProperty("total_points")
    private int totalPoints;

    @JsonProperty("subject")
    private String title;

    @JsonProperty("milestone")
    private long sprintId;

    @JsonProperty("milestone_name")
    private String sprintName;

    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSprintId() {
        return sprintId;
    }

    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.executor, flags);
        dest.writeInt(this.totalPoints);
        dest.writeString(this.title);
        dest.writeLong(this.sprintId);
        dest.writeString(this.sprintName);
        dest.writeInt(this.version);
    }

    public UserStory() {
    }

    protected UserStory(Parcel in) {
        this.id = in.readLong();
        this.executor = in.readParcelable(User.class.getClassLoader());
        this.totalPoints = in.readInt();
        this.title = in.readString();
        this.sprintId = in.readLong();
        this.sprintName = in.readString();
        this.version = in.readInt();
    }

    public static final Creator<UserStory> CREATOR = new Creator<UserStory>() {
        @Override
        public UserStory createFromParcel(Parcel source) {
            return new UserStory(source);
        }

        @Override
        public UserStory[] newArray(int size) {
            return new UserStory[size];
        }
    };
}
