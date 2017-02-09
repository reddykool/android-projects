package com.reddyz.kidzone;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Reddyz on 21-01-2017.
 */

public class GridItemAdapter extends ArrayAdapter<GridItemData> {
    public GridItemAdapter(Context context, int resource, ArrayList<GridItemData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;

        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_grid_item, parent, false);
            //gridItemView.setBackgroundResource(R.color.colorPrimaryDark);
        }

        GridItemData item = getItem(position);

        TextView gridItemPrimaryText = (TextView) gridItemView.findViewById(R.id.grid_item_text_primary);
        gridItemPrimaryText.setText(item.getName());

        ImageView gridItemImageDp = (ImageView) gridItemView.findViewById(R.id.grid_item_image_dp);
        gridItemImageDp.setImageResource(item.getImageResourceId());

        return gridItemView;
    }
}
