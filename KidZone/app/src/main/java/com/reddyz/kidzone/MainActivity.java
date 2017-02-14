package com.reddyz.kidzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * List of categories with exact class names, to dynamically load corresponding activity when
     * corresponding item in the list is selected from this main activity view.
     */
    private static String []mCategoriesClasses =
            { "AnimalsActivity", "BirdsActivity", "ColorsActivity", "FamilyMembersActivity",
              "RhymesActivity", "DanceSongsActivity", "InsectsActivity", "LullabiesActivity",
              "NumbersActivity", "VehiclesActivity", "VideosActivity"
            };

    /*
     * IMPORTANT:
     * Corresponding List of Display Names, which will be shown in the main activity list.
     * Hence, please make sure that this array is populated in sync with the above class names array
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItemData> categoriesList = new ArrayList<ListItemData>();
        categoriesList.add(new ListItemData("Animals", R.drawable.category_animals));
        categoriesList.add(new ListItemData("Birds", R.drawable.category_birds));
        categoriesList.add(new ListItemData("Colors", R.drawable.category_colors));
        categoriesList.add(new ListItemData("Family", R.drawable.category_family));
        categoriesList.add(new ListItemData("Rhymes", R.drawable.category_rhymes));
        categoriesList.add(new ListItemData("Dance Songs", R.drawable.category_dance_songs));
        categoriesList.add(new ListItemData("Insects", R.drawable.category_insects));
        categoriesList.add(new ListItemData("Lullabies", R.drawable.category_lullabies));
        categoriesList.add(new ListItemData("Numbers", R.drawable.category_numbers));
        categoriesList.add(new ListItemData("Vehicles", R.drawable.category_vehicles));
        categoriesList.add(new ListItemData("Videos", R.drawable.category_videos));

        // Get the listview resource from xml to populate the categories
        GridView mainView = (GridView) findViewById(R.id.main_activity_list);

        // Create the adapter to populate and dynamically show single row list categories.
        // TBD: Change the classnames to corresponding correct localised strings.
        MainListItemAdapter itemAdapter = new MainListItemAdapter(this, R.layout.main_list_item, categoriesList);

        // Link the adapter to Listview.
        mainView.setAdapter(itemAdapter);

        // When clicked on the list items, Open the correponding activity(thru Intents)
        mainView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classString = mCategoriesClasses[position];
                try {
                    Class className = Class.forName("com.reddyz.kidzone." + classString);
                    Intent i = new Intent(MainActivity.this, className);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
