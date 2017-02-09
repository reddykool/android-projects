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
public class InsectsFagment extends Fragment {

    /*
     * Default Basic Constructor
     */
    public InsectsFagment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Vehicles data to populate grid items.
        final ArrayList<GridItemData> insectsList = new ArrayList<GridItemData>();
        insectsList.add(new GridItemData("Ant", R.drawable.insect_ant, R.raw.default_audio));
        insectsList.add(new GridItemData("Bee", R.drawable.insect_bee, R.raw.default_audio));
        insectsList.add(new GridItemData("Beetle", R.drawable.insect_beetle, R.raw.default_audio));
        insectsList.add(new GridItemData("Butterfly", R.drawable.insect_butterfly, R.raw.default_audio));
        insectsList.add(new GridItemData("Caterpiller", R.drawable.insect_caterpillar, R.raw.default_audio));
        insectsList.add(new GridItemData("Cockroach", R.drawable.insect_cockroach, R.raw.default_audio));
        insectsList.add(new GridItemData("Cricket", R.drawable.insect_cricket, R.raw.default_audio));
        insectsList.add(new GridItemData("Dragon Fly", R.drawable.insect_dragonfly, R.raw.default_audio));
        insectsList.add(new GridItemData("Grasshopper", R.drawable.insect_grasshopper, R.raw.default_audio));
        insectsList.add(new GridItemData("House Fly", R.drawable.insect_housefly, R.raw.default_audio));
        insectsList.add(new GridItemData("Mosquito", R.drawable.insect_mosquito, R.raw.default_audio));
        insectsList.add(new GridItemData("Spider", R.drawable.insect_spider, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, insectsList);

        // Find the {@link GridView} object in the view hierarchy of the {@link VehiclesActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView insectsGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        insectsGridView.setAdapter(gridItemAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        insectsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ultimate Solution:
                // Open current item in a view pager activity.
                // When swipe left/right, it will go to next item from the same page directly
                Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, insectsList);
                viewPagerIntent.putExtra(GridItemViewPagerActivity.VIEW_PAGER_POSITION, position);
                startActivity(viewPagerIntent);
            }
        });

        return rootView;
    }

}
