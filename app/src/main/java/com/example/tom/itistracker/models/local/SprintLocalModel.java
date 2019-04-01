package com.example.tom.itistracker.models.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tom.itistracker.models.network.UserStory;

import java.util.List;

public class SprintLocalModel implements Parcelable {

    private long id;

    private boolean isClosed;

    private String name;

    private String startDate;

    private String finishDate;

    private int closedPoints;

    private int totalPoints;

    private List<UserStory> userStories;

    private int blockedTasksCount;

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

    public int getBlockedTasksCount() {
        return blockedTasksCount;
    }

    public void setBlockedTasksCount(int blockedTasksCount) {
        this.blockedTasksCount = blockedTasksCount;
    }

    public boolean isContainsBlockedTasks() {
        return blockedTasksCount > 0;
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
        dest.writeInt(this.blockedTasksCount);
    }

    public SprintLocalModel() {
    }

    protected SprintLocalModel(Parcel in) {
        this.id = in.readLong();
        this.isClosed = in.readByte() != 0;
        this.name = in.readString();
        this.startDate = in.readString();
        this.finishDate = in.readString();
        this.closedPoints = in.readInt();
        this.totalPoints = in.readInt();
        this.userStories = in.createTypedArrayList(UserStory.CREATOR);
        this.blockedTasksCount = in.readInt();
    }

    public static final Parcelable.Creator<SprintLocalModel> CREATOR = new Parcelable.Creator<SprintLocalModel>() {
        @Override
        public SprintLocalModel createFromParcel(Parcel source) {
            return new SprintLocalModel(source);
        }

        @Override
        public SprintLocalModel[] newArray(int size) {
            return new SprintLocalModel[size];
        }
    };
}
