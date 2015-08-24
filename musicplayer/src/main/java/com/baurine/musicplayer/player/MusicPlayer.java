package com.baurine.musicplayer.player;

import android.media.MediaPlayer;

/**
 * Created by baurine on 8/21/15.
 */
public class MusicPlayer implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
