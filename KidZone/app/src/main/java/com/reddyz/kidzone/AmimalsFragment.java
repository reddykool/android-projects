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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class AmimalsFragment extends Fragment {

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
    public AmimalsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Animals data to populate grid items.
        final ArrayList<GridItemData> animalsList = new ArrayList<GridItemData>();
        //animalsList.add("Buffalo");
        animalsList.add(new GridItemData("Bear", R.drawable.animal_bear, R.raw.default_audio));
        animalsList.add(new GridItemData("Camel", R.drawable.animal_camel, R.raw.default_audio));
        animalsList.add(new GridItemData("Cat", R.drawable.animal_cat, R.raw.default_audio));
        animalsList.add(new GridItemData("Cow", R.drawable.animal_cow, R.raw.default_audio));
        animalsList.add(new GridItemData("Deer", R.drawable.animal_deer, R.raw.default_audio));
        animalsList.add(new GridItemData("Dog", R.drawable.animal_dog, R.raw.default_audio));
        animalsList.add(new GridItemData("Donkey", R.drawable.animal_donkey, R.raw.default_audio));
        animalsList.add(new GridItemData("Elephant", R.drawable.animal_elephant, R.raw.default_audio));
        animalsList.add(new GridItemData("Giraffe", R.drawable.animal_giraffe, R.raw.default_audio));
        animalsList.add(new GridItemData("Goat", R.drawable.animal_goat, R.raw.default_audio));
        animalsList.add(new GridItemData("Horse", R.drawable.animal_horse, R.raw.default_audio));
        animalsList.add(new GridItemData("Kangaroo", R.drawable.animal_kangaroo, R.raw.default_audio));
        animalsList.add(new GridItemData("Lion", R.drawable.animal_lion, R.raw.default_audio));
        animalsList.add(new GridItemData("Monkey", R.drawable.animal_monkey, R.raw.default_audio));
        animalsList.add(new GridItemData("Pig", R.drawable.animal_pig, R.raw.default_audio));        //animalsList.add("Ox");
        animalsList.add(new GridItemData("Rabbit", R.drawable.animal_rabbit, R.raw.default_audio));
        animalsList.add(new GridItemData("Rat", R.drawable.animal_rat, R.raw.default_audio));
        animalsList.add(new GridItemData("Rhinoceros", R.drawable.animal_rhinoceros, R.raw.default_audio));
        animalsList.add(new GridItemData("Sheep", R.drawable.animal_sheep, R.raw.default_audio));
        animalsList.add(new GridItemData("Squirrel", R.drawable.animal_squirrel, R.raw.default_audio));
        animalsList.add(new GridItemData("Tiger", R.drawable.animal_tiger, R.raw.default_audio));
        animalsList.add(new GridItemData("Zebra", R.drawable.animal_zebra, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, animalsList);

        // Find the {@link GridView} object in the view hierarchy of the {@link AnimalsActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView animalsGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        animalsGridView.setAdapter(gridItemAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        animalsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //trying to open in Gallery - Not Working :(
                        /*
                        GridItemData currentItem = (GridItemData) parent.getItemAtPosition(position);
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        String uriString = "android.resource://" + getActivity().getPackageName() +
                                            "/" + currentItem.getImageResourceId();

                        Log.i("AnimalsFragment LOG: ", "Intent Uri String:: " + uriString);

                        Uri imagePath = Uri.parse(uriString);
                        intent.setDataAndType(imagePath, "image/*");
                        startActivity(intent);
                        */


                        // Create intent of Details Activity. Shows only this item as a full page activity
                        /*
                        GridItemData currentItem = (GridItemData) parent.getItemAtPosition(position);
                        Intent detailsIntent = new Intent(getActivity(), GridItemDetailsActivity.class);
                        detailsIntent.putExtra(GridItemDetailsActivity.DETAIL_NAME, currentItem.getName());
                        detailsIntent.putExtra(GridItemDetailsActivity.DETAIL_IMAGE, currentItem.getImageResourceId());
                        detailsIntent.putExtra(GridItemDetailsActivity.DETAIL_PAGE_TITLE, R.string.category_animals);

                        // Start details Activity
                        startActivity(detailsIntent);
                        */

                        // Ultimate Solution:
                        // Open current item in a view pager activity.
                        // When swipe left/right, it will go to next item from the same page directly
                        Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                        viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, animalsList);
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
