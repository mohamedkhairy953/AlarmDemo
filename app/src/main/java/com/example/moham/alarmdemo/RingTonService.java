package com.example.moham.alarmdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by moham on 6/14/2016.
 */
public class RingTonService extends Service {
    MediaPlayer media_player;
    boolean IsRunning;
    String song = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String extra = "";
        try {
            extra = intent.getExtras().getString("Extra");
        } catch (Exception e) {
        }
        NotificationManager notification_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent Go_to_Mainactivity_intent = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, Go_to_Mainactivity_intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Title")
                .setContentText("click on me")
                .setContentIntent(pending_intent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.search)
                .build();
        notification_manager.notify(0, notification);
        switch (extra) {
            case "Alarm_on":
                startId = 1;
                break;
            case "Alarm_off":
                media_player.stop();//
                startId = 0;
                break;
            default:
                startId = -1;
        }

        if (!IsRunning && startId == 1) {
                song = intent.getExtras().getString("song");

            switch (song) {
                case "star":
                    media_player = MediaPlayer.create(this, R.raw.start);

                    break;
                case "stop":
                    media_player = MediaPlayer.create(this, R.raw.stop);
                    break;
                case "start2":
                    media_player = MediaPlayer.create(this, R.raw.start2);
                    break;
                default:
                    media_player = MediaPlayer.create(this, R.raw.start);
            }
            media_player.start();
            startId = 0;
            IsRunning = true;
        } else if (IsRunning && startId == 0) {
            media_player.stop();
            media_player.reset();
            startId = 0;
            IsRunning = false;
        } else if (!IsRunning && startId == 0) {

            IsRunning = false;
            startId = 0;
        } else if (IsRunning && startId == 1) {
            IsRunning = true;
            startId = 1;
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(RingTonService.this, "Destroying", Toast.LENGTH_SHORT).show();
        media_player.stop();
    }
}
