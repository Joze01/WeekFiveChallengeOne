package com.applaudostudio.weekfivechallangeone.model;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * News item (model)
 */
public class ItemNews implements Parcelable {


    private String mNewId;
    private String mTitle;
    private String mThumbnailUrl;
    private String mTextBody;
    private String mWebUrl;
    private String mCategory;

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getNewId() {
        return mNewId;
    }

    public void setNewId(String mNewId) {
        this.mNewId = mNewId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    public String getTextBody() {
        return mTextBody;
    }

    public void setTextBody(String mTextBody) {
        this.mTextBody = mTextBody;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public void setWebUrl(String mWebUrl) {
        this.mWebUrl = mWebUrl;
    }


    public ItemNews() {
        this.mTextBody="";
        this.mTitle="";
        this.mThumbnailUrl="";
        this.mWebUrl="";
        this.mNewId="";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mNewId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mThumbnailUrl);
        dest.writeString(this.mTextBody);
        dest.writeString(this.mWebUrl);
    }

    protected ItemNews(Parcel in) {
        this.mNewId = in.readString();
        this.mTitle = in.readString();
        this.mThumbnailUrl = in.readString();
        this.mTextBody = in.readString();
        this.mWebUrl = in.readString();
    }

    public static final Creator<ItemNews> CREATOR = new Creator<ItemNews>() {
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
