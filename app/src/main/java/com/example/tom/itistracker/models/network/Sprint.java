package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint implements Parcelable {

    private long id;

    @JsonProperty("closed")
    private boolean isClosed;

    private String name;

    @JsonProperty("estimated_start")
    private String startDate;

    @JsonProperty("estimated_finish")
    private String finishDate;

    @JsonProperty("closed_points")
    private int closedPoints;

    @JsonProperty("total_points")
    private int totalPoints;

    @JsonProperty("user_stories")
    private List<UserStory> userStories;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getClosedPoints() {
        return closedPoints;
    }

    public void setClosedPoints(int closedPoints) {
        this.closedPoints = closedPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.isClosed ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.startDate);
        dest.writeString(this.finishDate);
        dest.writeInt(this.closedPoints);
        dest.writeInt(this.totalPoints);
        dest.writeTypedList(this.userStories);
    }

    public Sprint() {
    }

    protected Sprint(Parcel in) {
        this.id = in.readLong();
        this.isClosed = in.readByte() != 0;
        this.name = in.readString();
        this.startDate = in.readString();
        this.finishDate = in.readString();
        this.closedPoints = in.readInt();
        this.totalPoints = in.readInt();
        this.userStories = in.createTypedArrayList(UserStory.CREATOR);
    }

    public static final Creator<Sprint> CREATOR = new Creator<Sprint>() {
        @Override
        public Sprint createFromParcel(Parcel source) {
            return new Sprint(source);
        }

        @Override
        public Sprint[] newArray(int size) {
            return new Sprint[size];
        }
    };
}
