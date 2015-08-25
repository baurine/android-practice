package com.baurine.musicplayer.player;

import android.support.annotation.NonNull;

/**
 * Created by baurine on 8/24/15.
 */
public class Song {
    private long id;
    private String title;
    private String artist;

    private String url;

    private boolean netSong = false;

    public Song(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Song(@NonNull String url) {
        this.id = -1;
        this.title = "test";
        this.artist = "test";
        this.url = url;
        this.netSong = true;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    public boolean isNetSong() {
        return netSong;
    }

}
