package com.example.ehar.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;

/**
 * Created by ehar on 11/15/2016.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer player = null;
    private static MusicService instance = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // One time setup when service is initially created
    @Override
    public void onCreate() {
        super.onCreate();

        //player = MediaPlayer.create(this, R.raw.my_old_friend);
    }

    public static MusicService getInstance() {
        return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i =  super.onStartCommand(intent, flags, startId);
        player = new MediaPlayer();
        instance = this;

        File music = Environment.
                         getExternalStoragePublicDirectory(
                             Environment.DIRECTORY_MUSIC);

        String path = music.getPath() + "/" + "my_old_friend.mp3";
        try {
            player.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnPreparedListener(this);
        player.prepareAsync();
        return i;
    }

    public void play() {
        player.seekTo(0);
        player.start();
    }

    public void stop() {
        player.stop();
        player.release();
        instance = null;
        player = null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
