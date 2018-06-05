package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStoryExtra implements Parcelable {

    private long id;

    @JsonProperty("subject")
    private String title;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
    }

    public UserStoryExtra() {
    }

    protected UserStoryExtra(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
    }

    public static final Creator<UserStoryExtra> CREATOR = new Creator<UserStoryExtra>() {
        @Override
        public UserStoryExtra createFromParcel(Parcel source) {
            return new UserStoryExtra(source);
        }

        @Override
        public UserStoryExtra[] newArray(int size) {
            return new UserStoryExtra[size];
        }
    };
}
