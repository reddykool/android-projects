package com.reddyz.kidzone;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class VideosFragment extends Fragment {

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
    public VideosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Videos data to populate grid items.
        final ArrayList<GridItemVideoData> videoList = new ArrayList<GridItemVideoData>();
        videoList.add(new GridItemVideoData("All about Birds", R.drawable.thumbnail_all_about_birds, R.raw.video_all_about_birds, "4:19"));
        videoList.add(new GridItemVideoData("Cows Cows", R.drawable.thumbnail_cows_cows, R.raw.video_cows, "2:30"));
        videoList.add(new GridItemVideoData("Crazy Frog", R.drawable.thumbnail_crazy_frog, R.raw.video_crazy_frog, "3:51"));
        videoList.add(new GridItemVideoData("Dharani mandala", R.drawable.thumbnail_dharani_mandala, R.raw.video_dharani_mandala, "16:56"));
        videoList.add(new GridItemVideoData("Engine number nine", R.drawable.thumbnail_engine_number_nine, R.raw.video_engine_number_nine, "1:10"));
        videoList.add(new GridItemVideoData("Five litle Birds", R.drawable.thumbnail_five_litle_birds, R.raw.video_five_little_birds, "3:24"));
        videoList.add(new GridItemVideoData("Five little Monkeys", R.drawable.thumbnail_five_little_monkeys, R.raw.video_five_little_monkeys, "2:23"));
        videoList.add(new GridItemVideoData("Hakuna_matata", R.drawable.thumbnail_hakuna_matata, R.raw.video_hakuna_matata, "3:49"));
        videoList.add(new GridItemVideoData("I like to move it", R.drawable.thumbnail_i_like_to_move_it, R.raw.video_i_like_to_move_it, "2:50"));
        videoList.add(new GridItemVideoData("If you are Happy", R.drawable.thumbnail_if_you_are_happy, R.raw.video_if_you_are_happy, "2:06"));
        videoList.add(new GridItemVideoData("Mary had a little Lamb", R.drawable.thumbnail_marry_had_a_little_lamb, R.raw.video_mary_had_a_little_lamb, "1:34"));
        videoList.add(new GridItemVideoData("More Cows", R.drawable.thumbnail_more_cows, R.raw.video_cows_more, "2:53"));
        videoList.add(new GridItemVideoData("Old MacDonalds", R.drawable.thumbnail_old_macdonalds, R.raw.video_old_macdonald, "2:58"));
        videoList.add(new GridItemVideoData("Single Ladies", R.drawable.thumbnail_single_ladies, R.raw.video_single_ladies, "3:02"));
        videoList.add(new GridItemVideoData("Three little Kittens", R.drawable.thumbnail_three_lttle_kittens, R.raw.video_three_little_kittens_rio, "4:13"));
        videoList.add(new GridItemVideoData("Try Everything", R.drawable.thumbnail_try_everything, R.raw.video_try_everything, "3:22"));
        videoList.add(new GridItemVideoData("Two little Dicky Birds", R.drawable.thumbnail_two_litle_dicky_birds, R.raw.video_two_little_dicky_birds, "1:52"));
        videoList.add(new GridItemVideoData("Waka Waka", R.drawable.thumbnail_waka_waka, R.raw.video_waka_waka, "3:30"));
        videoList.add(new GridItemVideoData("Wheels on the Bus", R.drawable.thumbnail_wheels_on_the_bus, R.raw.video_wheels_on_the_bus, "2:18"));

        // Create a {@link GridItemVideoAdapter}, whose data source is a list of {@link GridItemVideoData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemVideoAdapter gridItemVideoAdapter = new GridItemVideoAdapter(getActivity(), R.layout.category_grid_item_video, videoList);

        // Find the {@link GridView} object in the view hierarchy of the {@link VideosActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        final GridView videoGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemVideoAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemVideoData} in the list.
        videoGridView.setAdapter(gridItemVideoAdapter);

        // Set a click listener to play the Video in a new activity, when the grid item is clicked on
        videoGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        GridItemVideoData currentItem = (GridItemVideoData) parent.getItemAtPosition(position);

                        // Create intent of Video Play Activity
                        Intent videoPlayIntent = new Intent(getActivity(), VideoPlayActivity.class);
                        videoPlayIntent.putExtra(VideoPlayActivity.VIDEO_PLAY_NAME, currentItem.getName());
                        videoPlayIntent.putExtra(VideoPlayActivity.VIDEO_PLAY_ID, currentItem.getAudioVideoResourceId());
                        videoPlayIntent.putExtra(VideoPlayActivity.VIDEO_PAGE_TITLE, R.string.category_videos);

                        //Start Activity
                        startActivity(videoPlayIntent);
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
