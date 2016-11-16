package com.example.ehar.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by ehar on 11/15/2016.
 */

public class MusicService extends Service {

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
        player = MediaPlayer.create(this, R.raw.my_old_friend);
        instance = this;
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
        play();
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
}
