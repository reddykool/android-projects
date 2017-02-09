package com.reddyz.kidzone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Reddyz on 06-02-2017.
 */

public class MainListItemAdapter extends ArrayAdapter<ListItemData> {

    public MainListItemAdapter(Context context, int resource, ArrayList<ListItemData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_item, parent, false);
        }

        ListItemData currentItem = getItem(position);

        TextView text = (TextView) listItemView.findViewById(R.id.main_list_item_text);
        ImageView image = (ImageView) listItemView.findViewById(R.id.main_list_item_image);

        text.setText(currentItem.getPrimaryText());
        image.setImageResource(currentItem.getImageResourceId());

        return listItemView;
    }
}
