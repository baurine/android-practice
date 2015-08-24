package com.baurine.musicplayer.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baurine.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by baurine on 8/24/15.
 */
public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    public SongAdapter(Context context, ArrayList<Song> songs) {
        this.songs = songs;
        songInf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // need to polish

        LinearLayout songLay = (LinearLayout) songInf.inflate(R.layout.item_song, parent, false);
        TextView titleView = (TextView) songLay.findViewById(R.id.tv_song_title);
        TextView artistView = (TextView) songLay.findViewById(R.id.tv_song_artist);

        Song curSong = songs.get(position);
        titleView.setText(curSong.getTitle());
        artistView.setText(curSong.getArtist());
        songLay.setTag(position);
        return songLay;
    }
}
