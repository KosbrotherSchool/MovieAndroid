package com.jasonko.movietime.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kolichung on 9/23/15.
 */
public class FollowMovieReceiver extends BroadcastReceiver {

    public static final String actionAlarm =  "register alarm from movietime";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(actionAlarm)) {
            context.startService(new Intent(context, FollowMovieService.class));
        }
    }

}