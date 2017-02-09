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

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class FamilyMembersFragment extends Fragment {

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
    public FamilyMembersFragment() {
        //TBD: Enable below super call and check the difference
        //super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Animal data to populate grid items.
        final ArrayList<GridItemData> familyMembersList = new ArrayList<GridItemData>();
        familyMembersList.add(new GridItemData("Cousin", R.drawable.family_megan, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Sister", R.drawable.family_ammu, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Me", R.drawable.family_shamanth, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Brother", R.drawable.family_rohan, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Mother", R.drawable.family_mom, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Father", R.drawable.family_dad, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Mother", R.drawable.family_mom_il, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Father", R.drawable.family_dad_il, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Aunty", R.drawable.family_chandu, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Uncle", R.drawable.family_kiran, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Aunty", R.drawable.family_shashi, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Uncle", R.drawable.family_adam, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Uncle", R.drawable.family_chaitu, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Uncle", R.drawable.family_mahesh, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Mother", R.drawable.family_mummy, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Grand Father", R.drawable.family_anayna, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Mother", R.drawable.family_siri, R.raw.default_audio));
        familyMembersList.add(new GridItemData("Father", R.drawable.family_puru, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemsAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, familyMembersList);

        // Find the {@link GridView} object in the view hierarchy of the {@link FamilyMembersActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView familyMembersGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        familyMembersGridView.setAdapter(gridItemsAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        familyMembersGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Ultimate Solution:
                        // Open current item in a view pager activity.
                        // When swipe left/right, it will go to next item from the same page directly
                        Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                        viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, familyMembersList);
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
