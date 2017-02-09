package com.reddyz.kidzone;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

public class GridItemViewPagerActivity extends AppCompatActivity {

    public static final String VIEW_PAGER_DATA = "DataList";
    public static final String VIEW_PAGER_POSITION = "Position";
    public static final int VIEW_PAGER_DEFAULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout/UI setting for FULLSCREEN mode
        {
            // Hide status bar(Android 4.1 and below)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            View decorView = getWindow().getDecorView();
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                          | View.SYSTEM_UI_FLAG_FULLSCREEN;

            //Spread the activity view Layout behind Navigation bar and status bar. FULLSCREEN
            int uiMoreOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_FULLSCREEN
                              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            //decorView.setSystemUiVisibility(uiMoreOptions);
        }
        setContentView(R.layout.activity_grid_item_view_pager);

        ArrayList<GridItemData> data = new ArrayList<GridItemData>();

        data = getIntent().getParcelableArrayListExtra(VIEW_PAGER_DATA);
        int position = getIntent().getIntExtra(VIEW_PAGER_POSITION, VIEW_PAGER_DEFAULT);

        ViewPager viewPager = (ViewPager) findViewById(R.id.grid_item_view_pager);
        GridItemPagerAdapter pagerAdapter = new GridItemPagerAdapter(this, data);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
    }
}
