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
public class RhymesFragment extends Fragment {

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
    public RhymesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of Rhymes data to populate list items.
        final ArrayList<ListItemData> rhymesList = new ArrayList<ListItemData>();
        rhymesList.add(new ListItemData("A B C D", "English", R.raw.rhymes_abcd));
        rhymesList.add(new ListItemData("Baa Baa black sheep", "English", R.raw.rhymes_ba_ba_black));
        rhymesList.add(new ListItemData("Chubby Cheeks", "English", R.raw.rhymes_chubby_cheeks));
        rhymesList.add(new ListItemData("Ding Dong Bell", "English", R.raw.rhymes_ding_dong_bell));
        rhymesList.add(new ListItemData("Five little monkeys", "English", R.raw.rhymes_five_little_monkeys));
        rhymesList.add(new ListItemData("Hickory Dickory Dock", "English", R.raw.rhymes_hickory_dickory_dock));
        rhymesList.add(new ListItemData("Hot cross Buns", "English", R.raw.rhymes_hot_cross_buns));
        rhymesList.add(new ListItemData("Humpty Dumpty", "English", R.raw.rhymes_humpty_dumpty));
        rhymesList.add(new ListItemData("Jack and Jill", "English", R.raw.rhymes_jack_and_jill));
        rhymesList.add(new ListItemData("Johny Johny yes Pappa", "English", R.raw.rhymes_johnny_johnny));
        rhymesList.add(new ListItemData("London Bridge is falling", "English", R.raw.rhymes_london_bridge));
        rhymesList.add(new ListItemData("Mary had a little lamb", "English", R.raw.rhymes_mary_had_lamb));
        //rhymesList.add(new ListItemData("Old McDonalds", "English", R.raw.london_bridge_rhymes));
        rhymesList.add(new ListItemData("One, Two, Buckle my Shoe", "English", R.raw.rhymes_one_two));
        rhymesList.add(new ListItemData("Rain rain go away", "English", R.raw.rhymes_rain_rain_go_away));
        rhymesList.add(new ListItemData("Ring-a-Ring o'Roses", "English", R.raw.rhymes_ringa_ringa));
        //rhymesList.add(new ListItemData("Three little Kittens", "English", R.raw.hot_cross_buns_rhymes));
        //rhymesList.add(new ListItemData("Twinkle Twinkle", "English", R.raw.chubby_cheeks_rhymes));
        //rhymesList.add(new ListItemData("Wheels on the Bus", "English", R.raw.rain_rain_go_away_rhymes));

        // Create a {@link listItemAdapter}, whose data source is a list of {@link ListItemData}s. The
        // adapter knows how to create list items for each item in the list.
        ListItemAdapter listItemAdapter = new ListItemAdapter(getActivity(), R.layout.category_list_item, rhymesList);

        // Find the {@link ListView} object in the view hierarchy of the {@link RhymesActivity}.
        // There should be a {@link ListView} with the view ID called category_list_view, which is
        // declared in the category_list.xml layout file.
        ListView rhymesListView = (ListView) rootView.findViewById(R.id.category_list_view);

        // Make the {@link ListView} use the {@link ListItemAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ListItemData} in the list.
        rhymesListView.setAdapter(listItemAdapter);

        // Set a click listener to play the audio when the list item is clicked on
        rhymesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int audioResourceId = rhymesList.get(position).getAudioResourceId();
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
        //releaseMediaPlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseMediaPlayer();
    }
}
