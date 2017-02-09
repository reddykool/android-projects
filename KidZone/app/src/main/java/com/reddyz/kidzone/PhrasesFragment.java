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
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class PhrasesFragment extends Fragment {

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
    public PhrasesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of phrases data to populate list items.
        final ArrayList<ListItemData> phrasesList = new ArrayList<ListItemData>();
        phrasesList.add(new ListItemData("Good morning"));
        phrasesList.add(new ListItemData("How are you?"));
        phrasesList.add(new ListItemData("I am feeling good"));
        phrasesList.add(new ListItemData("What is your name?"));
        phrasesList.add(new ListItemData("My name is..."));
        phrasesList.add(new ListItemData("Where are you going?"));
        phrasesList.add(new ListItemData("Let's go..."));
        phrasesList.add(new ListItemData("I am coming"));
        phrasesList.add(new ListItemData("Come here..."));
        phrasesList.add(new ListItemData("I am hungry"));

        // Create a {@link listItemAdapter}, whose data source is a list of {@link ListItemData}s. The
        // adapter knows how to create list items for each item in the list.
        ListItemAdapter listItemAdapter = new ListItemAdapter(getActivity(), R.layout.category_grid_item, phrasesList);

        // Find the {@link ListView} object in the view hierarchy of the {@link PhrasesActivity}.
        // There should be a {@link ListView} with the view ID called category_list_view, which is
        // declared in the category_list.xml layout file.
        ListView phrasesListView = (ListView) rootView.findViewById(R.id.category_list_view);

        // Make the {@link ListView} use the {@link ListItemAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ListItemData} in the list.
        phrasesListView.setAdapter(listItemAdapter);

        // Set a click listener to play the audio when the list item is clicked on
        phrasesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int audioResourceId = phrasesList.get(position).getAudioResourceId();
                        releaseMediaPlayer();

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
        releaseMediaPlayer();
    }
}
