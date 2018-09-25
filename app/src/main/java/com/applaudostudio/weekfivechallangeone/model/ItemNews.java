package com.applaudostudio.weekfivechallangeone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemNews implements Parcelable {
    private String mTitle;
    private String mThumbnailUrl;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setmThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public String getmTextBody() {
        return mTextBody;
    }

    public void setmTextBody(String mTextBody) {
        this.mTextBody = mTextBody;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public void setmWebUrl(String mWebUrl) {
        this.mWebUrl = mWebUrl;
    }

    private String mTextBody;
    private String mWebUrl;

    public ItemNews() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mThumbnailUrl);
        dest.writeString(this.mTextBody);
        dest.writeString(this.mWebUrl);
    }

    protected ItemNews(Parcel in) {
        this.mTitle = in.readString();
        this.mThumbnailUrl = in.readString();
        this.mTextBody = in.readString();
        this.mWebUrl = in.readString();
    }

    public static final Parcelable.Creator<ItemNews> CREATOR = new Parcelable.Creator<ItemNews>() {
        @Override
        public ItemNews createFromParcel(Parcel source) {
            return new ItemNews(source);
        }

        @Override
        public ItemNews[] newArray(int size) {
            return new ItemNews[size];
        }
    };
}
