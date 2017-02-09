package com.reddyz.kidzone;

import android.content.Intent;
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
 * Created by reddy on 28-Jan-17.
 */
public class BirdsFragment extends Fragment {

    /*
     * Default Basic Constructor
     */
    public BirdsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Birds data to populate grid items.
        final ArrayList<GridItemData> birdsList = new ArrayList<GridItemData>();
        birdsList.add(new GridItemData("Bulbul", R.drawable.bird_bulbul, R.raw.default_audio));
        birdsList.add(new GridItemData("Crane", R.drawable.bird_crane, R.raw.default_audio));
        birdsList.add(new GridItemData("Crow", R.drawable.bird_crow, R.raw.default_audio));
        birdsList.add(new GridItemData("Duck", R.drawable.bird_duck, R.raw.default_audio));
        birdsList.add(new GridItemData("Eagle", R.drawable.bird_eagle, R.raw.default_audio));
        birdsList.add(new GridItemData("Goose", R.drawable.bird_goose, R.raw.default_audio));
        birdsList.add(new GridItemData("Hen", R.drawable.bird_hen, R.raw.default_audio));
        birdsList.add(new GridItemData("Kingfisher", R.drawable.bird_kingfisher, R.raw.default_audio));
        birdsList.add(new GridItemData("Myna", R.drawable.bird_myna, R.raw.default_audio));
        birdsList.add(new GridItemData("Owl", R.drawable.bird_owl, R.raw.default_audio));
        birdsList.add(new GridItemData("Parrot", R.drawable.bird_parrot, R.raw.default_audio));
        birdsList.add(new GridItemData("Peacock", R.drawable.bird_peacock, R.raw.default_audio));
        birdsList.add(new GridItemData("Penguin", R.drawable.bird_penguin, R.raw.default_audio));
        birdsList.add(new GridItemData("Pigeon", R.drawable.bird_pigeon, R.raw.default_audio));
        birdsList.add(new GridItemData("Sparrow", R.drawable.bird_sparrow, R.raw.default_audio));
        birdsList.add(new GridItemData("Turkey", R.drawable.bird_turkey, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemAdapter = new GridItemAdapter(getActivity(),R.layout.category_grid_item, birdsList);

        // Find the {@link GridView} object in the view hierarchy of the {@link BirdsActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView birdsGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        birdsGridView.setAdapter(gridItemAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        birdsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ultimate Solution:
                // Open current item in a view pager activity.
                // When swipe left/right, it will go to next item from the same page directly
                Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, birdsList);
                viewPagerIntent.putExtra(GridItemViewPagerActivity.VIEW_PAGER_POSITION, position);
                startActivity(viewPagerIntent);
            }
        });

        return rootView;
    }
}
