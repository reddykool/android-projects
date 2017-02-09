package com.reddyz.kidzone;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reddyz on 05-02-2017.
 */
public class GridItemVideoAdapter extends ArrayAdapter<GridItemVideoData>{

    public GridItemVideoAdapter(Context context, int resource, ArrayList<GridItemVideoData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemVideoView = convertView;
        if(gridItemVideoView == null) {
            gridItemVideoView = LayoutInflater.from(getContext()).inflate(R.layout.category_grid_item_video, parent, false);
        }
        GridItemVideoData item = getItem(position);

        TextView videoNameText = (TextView) gridItemVideoView.findViewById(R.id.grid_item_video_name_text);
        videoNameText.setText(item.getName());

        ImageView videoImageThumbnail = (ImageView) gridItemVideoView.findViewById(R.id.grid_item_video_image_thumbnail);
        videoImageThumbnail.setImageResource(item.getImageResourceId());
        //Try to set video Thumbnail on grid view - Not working :(
        /*
        {
            Bitmap bmThumbnail;
            //String videoPath = "android.resource://" + getContext().getPackageName() + "/" + item.getAudioVideoResourceId();
            String videoPath = "android.resource://" + getContext().getPackageName() + "/raw/wa_vid_flower";
            Log.i("GridItemAdapter LOG: ", "Video Path:: " + videoPath);
            bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MICRO_KIND);
            if(bmThumbnail != null) {
                videoImageThumbnail.setImageBitmap(bmThumbnail);
            }else {
                videoImageThumbnail.setImageResource(R.drawable.ic_music_video_black_48dp);
            }
        }
        */

        TextView videoLengthText = (TextView) gridItemVideoView.findViewById(R.id.grid_item_video_length_text);
        videoLengthText.setText(item.getVideoLength());

        return gridItemVideoView;

    }
}
