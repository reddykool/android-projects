package com.reddyz.kidzone;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 21-01-2017.
 */
public class ListItemAdapter extends ArrayAdapter<ListItemData>{
    public ListItemAdapter(Context context, int resource, ArrayList<ListItemData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemListView = convertView;

        if(itemListView == null) {
            itemListView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item, parent, false);
        }

        ListItemData item = getItem(position);
        TextView listItemPrimaryText = (TextView) itemListView.findViewById(R.id.list_item_text_primary);
        listItemPrimaryText.setText(item.getPrimaryText());

        TextView listItemSecondaryText = (TextView) itemListView.findViewById(R.id.list_item_text_secondary);
        listItemSecondaryText.setText(item.getSecondaryText());

        /* Important: Note::
         * Since all the icosn are same and standard, they are set in xml directly.
         * If anytime, the icons or dps are different for each item in the list, set here accordingly
         * and enable the code.
         * /
        /*
        ImageView imageDP = (ImageView) itemListView.findViewById(R.id.list_item_image_dp);
        imageDP.setImageResource(R.drawable.ic_music_note_black_24dp);

        ImageView imageIcon = (ImageView) itemListView.findViewById(R.id.list_item_image_icon);
        imageIcon.setImageResource(R.drawable.ic_play_circle_outline_black_24dp);
        */

        return itemListView;
    }
}
