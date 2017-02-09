package com.reddyz.kidzone;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class NumbersFragment extends Fragment {

    //Player to play audio sound
    MediaPlayer mPlayer;

    //Audio manager to manage inter-app operations w.r.t sound.
    AudioManager mAudioManager;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed playing the audio file.
     */
    MediaPlayer.OnCompletionListener mMediaPlayerListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    AudioManager.OnAudioFocusChangeListener mAudioChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS :
                    //we've lost audio focus. Stop playback immediately and clean up resources
                    releaseMediaPlayer();
                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    // Pause playback and reset player to the start of the file.
                    mPlayer.pause();
                    mPlayer.seekTo(0);
                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    // Lower the volume, keep playing
                    mPlayer.pause();
                    break;

                case AudioManager.AUDIOFOCUS_GAIN :
                    // we have regained focus and can resume playback.
                    mPlayer.start();
                    break;
            }
        }
    };

    /**
     * Clean up the media player by releasing its resources.
     * Stop listening to Audio-Focus changes .
     */
    private void releaseMediaPlayer() {
        mAudioManager.abandonAudioFocus(mAudioChangeListener);
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    /*
     * Default Basic Constructor
     */
    public NumbersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Numbers data to populate grid items.
        final ArrayList<GridItemData> numbersList = new ArrayList<GridItemData>();
        numbersList.add(new GridItemData("Zero", R.drawable.number_zero, R.raw.default_audio));
        numbersList.add(new GridItemData("One", R.drawable.number_one, R.raw.default_audio));
        numbersList.add(new GridItemData("Two", R.drawable.number_two, R.raw.default_audio));
        numbersList.add(new GridItemData("Three", R.drawable.number_three, R.raw.default_audio));
        numbersList.add(new GridItemData("Four", R.drawable.number_four, R.raw.default_audio));
        numbersList.add(new GridItemData("Five", R.drawable.number_five, R.raw.default_audio));
        numbersList.add(new GridItemData("Six", R.drawable.number_six, R.raw.default_audio));
        numbersList.add(new GridItemData("Seven", R.drawable.number_seven, R.raw.default_audio));
        numbersList.add(new GridItemData("Eight", R.drawable.number_eight, R.raw.default_audio));
        numbersList.add(new GridItemData("Nine", R.drawable.number_nine, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter grisItemAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, numbersList);

        // Find the {@link GridView} object in the view hierarchy of the {@link NumbersActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView numbersGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        numbersGridView.setAdapter(grisItemAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        numbersGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Ultimate Solution:
                        // Open current item in a view pager activity.
                        // When swipe left/right, it will go to next item from the same page directly
                        Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                        viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, numbersList);
                        viewPagerIntent.putExtra(GridItemViewPagerActivity.VIEW_PAGER_POSITION, position);
                        startActivity(viewPagerIntent);
                    }
                }
        );

        return rootView;
    }

    /*
     * Release media player when activity goes to onPause mode.
     */
    @Override
    public void onPause() {
        super.onPause();
        //releaseMediaPlayer();
    }
}
