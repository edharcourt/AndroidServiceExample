package com.example.ehar.serviceexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

    private static final int NOTIFICATION_ID = 123;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // One time setup when service is initially created
    @Override
    public void onCreate() {
        super.onCreate();

        // Don't create a media player this way since the
        // media is not a resource but an external file.
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

        // cancel the notification
        NotificationManager nmgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nmgr.cancel(NOTIFICATION_ID);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();

        // Build a notification for the playing song
        Notification.Builder b = new Notification.Builder(this).
                setSmallIcon(R.drawable.placeholder).
                setContentTitle("Harcourtify").
                setContentText("Stop");

        NotificationManager nmgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(resultPendingIntent);
        nmgr.notify(NOTIFICATION_ID, b.build());

    }
}
