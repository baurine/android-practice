package com.baurine.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MP3_URL = "http://192.168.0.215:3000" +
            "/uploads/track/file/20/low_quality_4_masazumi_ozawa_attraction.mp3";

    private MediaPlayer mediaPlayer;

    private EditText edtxUrl;

    private boolean prepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtons();
        edtxUrl = (EditText) findViewById(R.id.edtx_url);
        edtxUrl.setText(MP3_URL);
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    private void setupButtons() {
        Button btnStart, btnPlayPause, btnStop;
        btnStart = (Button) findViewById(R.id.btn_start);
        btnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        btnStop = (Button) findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(this);
        btnPlayPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startPlay();
                break;
            case R.id.btn_play_pause:
                pausePlay();
                break;
            case R.id.btn_stop:
                stopPlay();
                break;
            default:
                break;
        }
    }

    private void startPlay() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.reset();
        prepared = false;
        try {
            mediaPlayer.setDataSource(edtxUrl.getText().toString());
            mediaPlayer.prepareAsync();
//            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                prepared = true;
                mp.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });
    }

    private void pausePlay() {
        if (mediaPlayer != null && prepared) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    private void stopPlay() {
        if (mediaPlayer != null && prepared) {
            mediaPlayer.stop();
        }
    }
}
