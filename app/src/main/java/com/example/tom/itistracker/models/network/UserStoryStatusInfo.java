package com.example.tom.itistracker.models.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStoryStatusInfo implements Parcelable {

    @JsonProperty("status_extra_info")
    private UserStoryStatus mUserStoryStatus;

    public UserStoryStatus getUserStoryStatus() {
        return mUserStoryStatus;
    }

    public void setUserStoryStatus(UserStoryStatus userStoryStatus) {
        mUserStoryStatus = userStoryStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mUserStoryStatus == null ? -1 : this.mUserStoryStatus.ordinal());
    }

    public UserStoryStatusInfo() {
    }

    protected UserStoryStatusInfo(Parcel in) {
        int tmpMUserStoryStatus = in.readInt();
        this.mUserStoryStatus = tmpMUserStoryStatus == -1 ? null : UserStoryStatus.values()[tmpMUserStoryStatus];
    }

    public static final Parcelable.Creator<UserStoryStatusInfo> CREATOR = new Parcelable.Creator<UserStoryStatusInfo>() {
        @Override
        public UserStoryStatusInfo createFromParcel(Parcel source) {
            return new UserStoryStatusInfo(source);
        }

        @Override
        public UserStoryStatusInfo[] newArray(int size) {
            return new UserStoryStatusInfo[size];
        }
    };
}
