package com.reddyz.kidzone;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class DanceSongsFragment extends Fragment {

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
    public DanceSongsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of songs data to populate list items.
        final ArrayList<ListItemData> danceSongsList = new ArrayList<ListItemData>();
        danceSongsList.add(new ListItemData("Aluma Doluma", "Tamil", R.raw.ds_aaluma_doluma));
        danceSongsList.add(new ListItemData("Chammak Challu", "Hindi", R.raw.ds_chammak_challo));
        danceSongsList.add(new ListItemData("Chicken Dance", "English", R.raw.ds_chicken_dance));
        danceSongsList.add(new ListItemData("Cinema choopista Mama", "Telugu", R.raw.ds_cinema_choopistha_mava));
        danceSongsList.add(new ListItemData("Crazy Frog", "English", R.raw.ds_crazy_frog));
        danceSongsList.add(new ListItemData("Gangnam Style", "English", R.raw.ds_gangnam_style));
        danceSongsList.add(new ListItemData("Happy Happy", "English - Pharell Williams", R.raw.ds_happy_happy));
        danceSongsList.add(new ListItemData("Humma Humma", "Hindi", R.raw.ds_humma_humma_orig));
        //danceSongsList.add(new ListItemData("I like to Move it", "English - Madagascar"));
        //danceSongsList.add(new ListItemData("Kaala  Chashma", "Hindi", R.raw.));
        danceSongsList.add(new ListItemData("Kevvu Keka", "Telugu", R.raw.ds_kevvu_keka));
        danceSongsList.add(new ListItemData("Lungi Dance", "Hindi", R.raw.ds_lungi_dance));
        danceSongsList.add(new ListItemData("Mauja hi Mauja", "Hindi", R.raw.ds_mauja_hi_mauja));
        danceSongsList.add(new ListItemData("Pungi bhajao", "Hindi", R.raw.ds_pungi_bhajao));
        danceSongsList.add(new ListItemData("Soooper Machi", "Telugu", R.raw.ds_super_machi));
        //danceSongsList.add(new ListItemData("The Ketchup Song", "English", R.raw.));
        danceSongsList.add(new ListItemData("Waka Waka", "English - Shakira", R.raw.ds_waka_waka));

        // Create a {@link listItemAdapter}, whose data source is a list of {@link ListItemData}s. The
        // adapter knows how to create list items for each item in the list.
        ListItemAdapter listItemAdapter = new ListItemAdapter(getActivity(), R.layout.category_list_item, danceSongsList);

        // Find the {@link ListView} object in the view hierarchy of the {@link DanceSongsActivity}.
        // There should be a {@link ListView} with the view ID called category_list_view, which is
        // declared in the category_list.xml layout file.
        ListView danceSongsListView = (ListView)rootView.findViewById(R.id.category_list_view);

        // Make the {@link ListView} use the {@link ListItemAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ListItemData} in the list.
        danceSongsListView.setAdapter(listItemAdapter);

        // Set a click listener to play the audio when the list item is clicked on
        danceSongsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int audioResourceId = danceSongsList.get(position).getAudioResourceId();
                        releaseMediaPlayer();

                        /*
                        ImageView icon = (ImageView) view.findViewById(R.id.list_item_image_icon);
                        icon.setImageResource(R.drawable.ic_pause_circle_outline_black_36dp);
                        */

                        //Request for audio focus to play the associated frid item audio.
                        int result = mAudioManager.requestAudioFocus(mAudioChangeListener,
                                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            //Access granted. play the associated audio.
                            mPlayer = MediaPlayer.create(getActivity(), audioResourceId);
                            mPlayer.start();

                            // Setup a mediaPLayer listener , so that we can release the
                            // resources when play completed.
                            mPlayer.setOnCompletionListener(mMediaPlayerListener);
                        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }
}
