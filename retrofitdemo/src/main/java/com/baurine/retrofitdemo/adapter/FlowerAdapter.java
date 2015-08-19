package com.baurine.retrofitdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baurine.retrofitdemo.R;
import com.baurine.retrofitdemo.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by baurine on 8/19/15.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

    private static final String URL = "http://services.hanselandpetal.com/photos/";

    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flower flower = getItem(position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_flower, parent,
                    false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_flower);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder = new ViewHolder(imageView, textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTvName.setText(flower.getName());
        Picasso.with(getContext())
                .load(URL + flower.getPhoto())
                .resize(200, 200)
                .into(viewHolder.mIvImage);

        return convertView;
    }

    public static class ViewHolder {
        public ImageView mIvImage;
        public TextView mTvName;

        public ViewHolder(ImageView imageView, TextView textView) {
            this.mIvImage = imageView;
            this.mTvName = textView;
        }
    }
}
