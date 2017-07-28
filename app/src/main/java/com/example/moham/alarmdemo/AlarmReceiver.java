package com.example.moham.alarmdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by moham on 6/14/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getExtras().getString("Extra");
        String song =intent.getExtras().getString("song");
        Log.d("RRRRRR", extra);
        Intent Go_to_service_intent = new Intent(context, RingTonService.class);
        Go_to_service_intent.putExtra("Extra",extra);
        Log.e("ssss",song);
            Go_to_service_intent.putExtra("song",song);

        context.startService(Go_to_service_intent);
    }
//
}
