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
public class LullabiesFragment extends Fragment {

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
    public LullabiesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of songs data to populate list items.
        final ArrayList<ListItemData> lullabiesList = new ArrayList<ListItemData>();
        lullabiesList.add(new ListItemData("Bahubali Theme", "Telugu - Bahubali", R.raw.lullabies_bahubali_theme));
        lullabiesList.add(new ListItemData("Jai CHiranjeeva", "Telugu - JVAS", R.raw.lullabies_jai_chiranjeeva));
        lullabiesList.add(new ListItemData("Laali Jo Lali Jo", "Telugu", R.raw.lullabies_laali_jo_lali_jo));
        lullabiesList.add(new ListItemData("Laali Laali", "Telugu - Swathi Mutyam", R.raw.lullabies_laali_laali));
        lullabiesList.add(new ListItemData("Nammakame Ivvara", "Telugu - Puli", R.raw.lullabies_namakame_ivvara));
        lullabiesList.add(new ListItemData("Rama Laali", "Telugu", R.raw.lullabies_rama_laali));
        lullabiesList.add(new ListItemData("Yeh Mat Kaho", "Hindi", R.raw.lullabies_ye_mat_kaho_kuda_se));

        // Create a {@link listItemAdapter}, whose data source is a list of {@link ListItemData}s. The
        // adapter knows how to create list items for each item in the list.
        ListItemAdapter listItemAdapter = new ListItemAdapter(getActivity(), R.layout.category_list_item, lullabiesList);

        // Find the {@link ListView} object in the view hierarchy of the {@link LullabiesActivity}.
        // There should be a {@link ListView} with the view ID called category_list_view, which is
        // declared in the category_list.xml layout file.
        ListView lullabiesListView = (ListView) rootView.findViewById(R.id.category_list_view);

        // Make the {@link ListView} use the {@link ListItemAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ListItemData} in the list.
        lullabiesListView.setAdapter(listItemAdapter);

        // Set a click listener to play the audio when the list item is clicked on
        lullabiesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int audioResourceId = lullabiesList.get(position).getAudioResourceId();
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
