package com.baurine.musicplayer.player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baurine.musicplayer.MusicPlayActivity;
import com.baurine.musicplayer.R;

import java.util.List;

/**
 * Created by baurine on 8/24/15.
 */
public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;
    private List<Song> songs;
    private int songPos;

    private final IBinder musicBinder = new MusicBinder();
    private String songTitle = "";
    private static final int NOTIFY_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        songPos = 0;
        mediaPlayer = new MediaPlayer();

        initMusicPlayer();
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        stopForeground(true);
    }

    private void initMusicPlayer() {
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    public void setList(List<Song> theSongs) {
        songs = theSongs;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();

        setNotification();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public void playSong() {
        mediaPlayer.reset();

        Song playSong = songs.get(songPos);
        songTitle = playSong.getTitle();
        long songId = playSong.getId();
        Uri trackUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songId);

        try {
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Log.e("Music Service", "Error set data source", e);
        }
    }

    public void setSong(int songIndex) {
        songPos = songIndex;
    }

    public int getPos() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDur() {
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer() {
        mediaPlayer.pause();
    }

    public void seek(int pos) {
        mediaPlayer.seekTo(pos);
    }

    public void go() {
        mediaPlayer.start();
    }

    public void playPrev() {
        songPos--;
        if (songPos < 0) songPos = songs.size() - 1;
        playSong();
    }

    public void playNext() {
        songPos++;
        if (songPos >= songs.size()) songPos = 0;
        playSong();
    }

    private void setNotification() {
        Intent notifyIntent = new Intent(this, MusicPlayActivity.class);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendIntent)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification notification = builder.build();
        startForeground(NOTIFY_ID, notification);
    }

}
