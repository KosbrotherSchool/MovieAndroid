package com.jasonko.movietime.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by kolichung on 9/7/15.
 */
public class MovieBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            setRepeatAlarm(context);
        }
    }

    private void setRepeatAlarm(Context context) {

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 30);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(context, FollowMovieReceiver.class);
        serviceIntent.setAction(FollowMovieReceiver.actionAlarm);
        if (!alarmUp(context)) {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, serviceIntent, 0);
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }

    private boolean alarmUp(Context context) {
        return PendingIntent.getBroadcast(context, 0,
                new Intent("register alarm from movietime"),
                PendingIntent.FLAG_NO_CREATE) != null;
    }
}