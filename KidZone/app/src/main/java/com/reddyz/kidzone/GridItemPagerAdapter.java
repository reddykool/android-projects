package com.reddyz.kidzone;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 04-02-2017.
 */
public class GridItemPagerAdapter extends PagerAdapter {

    private Activity mActivity;
    private ArrayList<GridItemData> mGridItemList;

    public GridItemPagerAdapter(Activity activity, ArrayList<GridItemData> list) {
        mActivity = activity;
        mGridItemList = list;
    }

    @Override
    public int getCount() {
        return mGridItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View gridItemDetailView = LayoutInflater.from(mActivity).inflate(R.layout.grid_item_details, container, false);

        ImageView detailImageView = (ImageView) gridItemDetailView.findViewById(R.id.grid_item_detail_image);
        TextView detailTextView = (TextView) gridItemDetailView.findViewById(R.id.grid_item_detail_text);

        GridItemData currentItem = mGridItemList.get(position);
        detailImageView.setImageResource(currentItem.getImageResourceId());
        detailTextView.setText(currentItem.getName());

        container.addView(gridItemDetailView);

        return gridItemDetailView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
