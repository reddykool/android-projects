package com.reddyz.kidzone;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity {

    static final String VIDEO_PAGE_TITLE = "Title";
    static final String VIDEO_PLAY_ID = "VideoId";
    static final String VIDEO_PLAY_NAME = "VideoName";
    static final String VIDEO_CURRENT_POSITION = "Position";
    static final String LOG_TAG = "VideoPlayActivity";
    static final int DEFAULT = -1;

    VideoView mVideoPlayView;
    int mPosition = 0;

    //private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, " OnCreate()");
        super.onCreate(savedInstanceState);

        // Hide status bar(Android 4.1 and below)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_play);

        String videoName = getIntent().getStringExtra(VIDEO_PLAY_NAME);
        int videoResourceId = getIntent().getIntExtra(VIDEO_PLAY_ID, DEFAULT);
        int titleResourceId = getIntent().getIntExtra(VIDEO_PAGE_TITLE, DEFAULT);

        String titleString = getString(titleResourceId) + "  >  " + videoName;
        setTitle(titleString);

        mVideoPlayView = (VideoView) findViewById(R.id.videoView);
        final Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResourceId);
        MediaController mediaControls = new MediaController(this);

        mediaControls.setAnchorView(mVideoPlayView);
        mVideoPlayView.setMediaController(mediaControls);
        mVideoPlayView.setVideoURI(videoUri);

        mVideoPlayView.requestFocus();
        mVideoPlayView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoPlayView.seekTo(mPosition);
                mVideoPlayView.start();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(LOG_TAG, " onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        mVideoPlayView.pause();
        mPosition = mVideoPlayView.getCurrentPosition();
        outState.putInt(VIDEO_CURRENT_POSITION, mPosition);
        Log.i(LOG_TAG, " onSaveInstanceState() - Position: " + mPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(LOG_TAG, " onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
        mPosition = savedInstanceState.getInt(VIDEO_CURRENT_POSITION);
        mVideoPlayView.seekTo(mPosition);
        Log.i(LOG_TAG, " onRestoreInstanceState() - Position: " + mPosition);
    }
}
