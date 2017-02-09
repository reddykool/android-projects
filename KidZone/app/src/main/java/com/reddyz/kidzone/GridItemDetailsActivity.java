package com.reddyz.kidzone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemDetailsActivity extends AppCompatActivity {

    static final int DEFAULT = -1;
    static final String DETAIL_NAME = "Name";
    static final String DETAIL_IMAGE = "Image";
    static final String DETAIL_PAGE_TITLE = "Title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_item_details);

        // set this activity window for FULLSCREEN mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String name = getIntent().getStringExtra(DETAIL_NAME);
        int imageResourceId = getIntent().getIntExtra(DETAIL_IMAGE, DEFAULT);
        int titleResourceId = getIntent().getIntExtra(DETAIL_PAGE_TITLE, DEFAULT);

        TextView nameText = (TextView) findViewById(R.id.grid_item_detail_text);
        nameText.setText(name);

        if(imageResourceId != DEFAULT ) {
            ImageView imageView = (ImageView) findViewById(R.id.grid_item_detail_image);
            imageView.setImageResource(imageResourceId);
        }

        if(titleResourceId != DEFAULT)
            setTitle(titleResourceId);
    }
}
