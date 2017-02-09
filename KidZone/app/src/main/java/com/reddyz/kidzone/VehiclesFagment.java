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
public class VehiclesFagment extends Fragment {

    /*
     * Default Basic Constructor
     */
    public VehiclesFagment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_grid, container, false);

        // Create a list of Vehicles data to populate grid items.
        final ArrayList<GridItemData> vehiclesList = new ArrayList<GridItemData>();
        vehiclesList.add(new GridItemData("Aeroplane", R.drawable.vehicle_aeroplane, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Auto", R.drawable.vehicle_auto, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Bike", R.drawable.vehicle_bike, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Boat", R.drawable.vehicle_boat, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Bus", R.drawable.vehicle_bus, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Car", R.drawable.vehicle_car, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Cycle", R.drawable.vehicle_cycle, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Helicopter", R.drawable.vehicle_helicopter, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Jeep", R.drawable.vehicle_jeep, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Jet", R.drawable.vehicle_jet, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Lorry", R.drawable.vehicle_lorry, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Motor Cycle", R.drawable.vehicle_motorcycle, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Rocket", R.drawable.vehicle_rocket, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Ship", R.drawable.vehicle_ship, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Tractor", R.drawable.vehicle_tractor, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Train", R.drawable.vehicle_train, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Truck", R.drawable.vehicle_truck, R.raw.default_audio));
        vehiclesList.add(new GridItemData("Van", R.drawable.vehicle_van, R.raw.default_audio));
        //vehiclesList.add(new GridItemData("Yatch", R.drawable.vehicle_workinprogress, R.raw.default_audio));

        // Create a {@link GridItemAdapter}, whose data source is a list of {@link GridItemData}s. The
        // adapter knows how to create list items for each item in the list.
        GridItemAdapter gridItemAdapter = new GridItemAdapter(getActivity(), R.layout.category_grid_item, vehiclesList);

        // Find the {@link GridView} object in the view hierarchy of the {@link VehiclesActivity}.
        // There should be a {@link GridView} with the view ID called category_grid_view, which is
        // declared in the category_grid.xml layout file.
        GridView vehiclesGridView = (GridView) rootView.findViewById(R.id.category_grid_view);

        // Make the {@link GridView} use the {@link GridItemAdapter} we created above, so that the
        // {@link GridView} will display grid items for each {@link GridItemData} in the list.
        vehiclesGridView.setAdapter(gridItemAdapter);

        // Set a click listener to open full page detail view when the grid item is clicked on
        vehiclesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ultimate Solution:
                // Open current item in a view pager activity.
                // When swipe left/right, it will go to next item from the same page directly
                Intent viewPagerIntent = new Intent(getActivity(), GridItemViewPagerActivity.class);
                viewPagerIntent.putParcelableArrayListExtra(GridItemViewPagerActivity.VIEW_PAGER_DATA, vehiclesList);
                viewPagerIntent.putExtra(GridItemViewPagerActivity.VIEW_PAGER_POSITION, position);
                startActivity(viewPagerIntent);
            }
        });


        return rootView;
    }
}
