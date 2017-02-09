package com.reddyz.kidzone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InsectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.category_container, new InsectsFagment())
                .commit();
    }
}
