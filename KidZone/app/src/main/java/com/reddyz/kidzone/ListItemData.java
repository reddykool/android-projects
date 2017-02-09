package com.reddyz.kidzone;

/**
 * Created by Reddyz on 24-01-2017.
 */

public class ListItemData {
    private String mPrimaryText;
    private String mSecondaryText;
    private int mAudioResourceId;
    private int mImageResourceId;

    public ListItemData(String primaryText, String secondaryText, int audioResourceId) {
        this.mPrimaryText = primaryText;
        this.mSecondaryText = secondaryText;
        this.mAudioResourceId = audioResourceId;
        this.mImageResourceId = R.drawable.workinprogress;
    }

    public ListItemData(String primaryText, int imageResourceId) {
        this.mPrimaryText = primaryText;
        this.mSecondaryText = primaryText;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = R.raw.default_audio;
    }

    public ListItemData(String primaryText, String secondaryText) {
        this.mPrimaryText = primaryText;
        this.mSecondaryText = secondaryText;
        this.mAudioResourceId = R.raw.default_audio;
        this.mImageResourceId = R.drawable.workinprogress;
    }

    public ListItemData(String primaryText) {
        this.mPrimaryText = primaryText;
        this.mSecondaryText = primaryText;
        this.mAudioResourceId = R.raw.default_audio;
        this.mImageResourceId = R.drawable.workinprogress;
    }

    public String getPrimaryText() {
        return mPrimaryText;
    }

    public String getSecondaryText() {
        return mSecondaryText;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

}
