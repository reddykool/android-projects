package com.reddyz.kidzone;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Reddyz on 23-01-2017.
 */

public class GridItemData implements Parcelable{
    private String mName;
    private int mImageResourceId;
    private int mAudioVideoResourceId;

    public GridItemData(String name) {
        this.mName = name;
        this.mImageResourceId = R.drawable.workinprogress;
        this.mAudioVideoResourceId = R.raw.default_audio;
    }

    public GridItemData(String name, int imageResourceId, int audioVideoResourceId) {
        this.mName = name;
        this.mImageResourceId = imageResourceId;
        this.mAudioVideoResourceId = audioVideoResourceId;
    }

    public String getName() {
        return mName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioVideoResourceId() {
        return mAudioVideoResourceId;
    }


    // Implementing Parcelable interface
    protected GridItemData(Parcel in) {
        mName = in.readString();
        mImageResourceId = in.readInt();
        mAudioVideoResourceId = in.readInt();
    }

    public static final Creator<GridItemData> CREATOR = new Creator<GridItemData>() {
        @Override
        public GridItemData createFromParcel(Parcel in) {
            return new GridItemData(in);
        }

        @Override
        public GridItemData[] newArray(int size) {
            return new GridItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mImageResourceId);
        dest.writeInt(mAudioVideoResourceId);
    }
}
