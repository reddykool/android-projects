package com.reddyz.kidzone;

/**
 * Created by Reddyz on 05-02-2017.
 */

public class GridItemVideoData {
    private String mName;
    private int mImageResourceId;
    private int mVideoResourceId;
    private String mVideoLength;

    public GridItemVideoData(String name, int imageResourceId, int videoResourceId, String length) {
        this.mName = name;
        this.mImageResourceId = imageResourceId;
        this.mVideoResourceId = videoResourceId;
        this.mVideoLength = length;
    }

    public String getName() {
        return mName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioVideoResourceId() {
        return mVideoResourceId;
    }

    public String getVideoLength() {
        return mVideoLength;
    }
}
