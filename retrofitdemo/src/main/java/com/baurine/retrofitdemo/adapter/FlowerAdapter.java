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
import com.baurine.retrofitdemo.view.BlurTransformation;
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
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTvName.setText(flower.getName());

        Picasso.with(getContext())
                .load(URL + flower.getPhoto())
                .transform(new BlurTransformation(getContext(), 25))
                .into(viewHolder.mIvImage);

        return convertView;
    }

    public static class ViewHolder {
        public ViewGroup mLlContent;
        public ImageView mIvImage;
        public TextView mTvName;

        public ViewHolder(View convertView) {
            this.mLlContent = (ViewGroup) convertView.findViewById(R.id.ll_content);
            this.mIvImage = (ImageView) convertView.findViewById(R.id.iv_flower);
            this.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
        }
    }
}
