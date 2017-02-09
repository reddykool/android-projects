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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 20-01-2017.
 */
public class ColorsFragment extends Fragment {

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
    public ColorsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container,false);

        // Create a list of Colors data to populate grid items.
        final ArrayList<GridItemData> colorsList = new ArrayList<GridItemData>();
        colorsList.add(new GridItemData("Black", R.drawable.color_black, R.raw.default_audio));
        colorsList.add(new GridItemData("Blue", R.drawable.color_blue, R.raw.default_audio));
        colorsList.add(new GridItemData("Brown", R.drawable.color_brown, R.raw.default_audio));
        colorsList.add(new GridItemData("Green", R.drawable.color_green, R.raw.default_audio));
        colorsList.add(new GridItemData("Grey", R.drawable.color_grey, R.raw.default_audio));
        colorsList.add(new GridItemData("Orange", R.drawable.color_orange, R.raw.default_audio));
        colorsList.add(new GridItemData("Pink", R.drawable.color_pink, R.raw.default_audio));
        colorsList.add(new GridItemData("Purple", R.drawable.color_purple, R.raw.default_audio));
        colorsList.add(new GridItemData("Red", R.drawable.color_red, R.raw.default_audio));
        colorsList.add(new GridItemData("White", R.drawable.color_white, R.raw.default_audio));
        colorsList.add(new GridItemData("Yellow", R.drawable.color_yellow, R.raw.default_audio));
        //colorsList.add(new GridItemData("Grey"));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemsAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, colorsList);

        // Find the {@link GridView} object in the view hierarchy of the {@link ColorsActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView colorsGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        colorsGridView.setAdapter(gridItemsAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        colorsGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Ultimate Solution:
                        // Open current item in a view pager activity.
                        // When swipe left/right, it will go to next item from the same page directly
                        Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                        viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, colorsList);
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
