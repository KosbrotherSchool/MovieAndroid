package com.jasonko.movietime.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.jasonko.movietime.MainActivity;
import com.jasonko.movietime.R;
import com.jasonko.movietime.dao.DaoMaster;
import com.jasonko.movietime.dao.DaoSession;
import com.jasonko.movietime.dao.FollowMovie;
import com.jasonko.movietime.dao.FollowMovieDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kolichung on 9/7/15.
 */
public class FollowMovieService extends IntentService {

    public FollowMovieService() {
        super(FollowMovieService.class.getName());
    }
    public SharedPreferences mSharedPreference;

    @Override
    protected void onHandleIntent(Intent workIntent) {
        mSharedPreference = getSharedPreferences(null, 0);
        boolean theDayBoolean = mSharedPreference.getBoolean("the_day", true);
        boolean customDayBoolean = mSharedPreference.getBoolean("custom_day", false);
        int customDayInt = mSharedPreference.getInt("custom_day_int", 3);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "expense", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        FollowMovieDao movieDao = daoSession.getFollowMovieDao();

        ArrayList<FollowMovie> mMovies = (ArrayList) movieDao.queryBuilder().orderDesc(FollowMovieDao.Properties.Update_date).list();
        Calendar c = Calendar.getInstance();
        for (int i=0; i<mMovies.size(); i++){
            Calendar movieCal = Calendar.getInstance();
            Date movieDate = mMovies.get(i).getPublish_day();
            movieCal.setTime(movieDate);
            if (theDayBoolean) {
                if (isIntervalLessXDay(c, movieCal, 0)) {
                    makeNotification("電影即時通", mMovies.get(i).getTitle() + "今日上映囉！");
                }
            }
            if (customDayBoolean){
                if (isIntervalLessXDay(c, movieCal, customDayInt)) {
                    makeNotification("電影即時通", mMovies.get(i).getTitle() + "快要上映囉！");
                }
            }
        }

    }

    private void makeNotification(String appTitle, String subTitle) {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification noti;
        if (Build.VERSION.SDK_INT < 16) {
            noti = new Notification.Builder(this)
                    .setContentTitle(appTitle).setContentText(subTitle)
                    .setSmallIcon(R.mipmap.ic_launcher).getNotification();
        } else {
            noti = new Notification.Builder(this)
                    .setContentTitle(appTitle)
                    .setContentText(subTitle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent).build();
        }

        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);

    }

    public boolean isIntervalLessXDay(Calendar c1, Calendar c2, int x)
    {
        long milliSeconds1 = c1.getTimeInMillis();
        long milliSeconds2 = c2.getTimeInMillis();
        long periodSeconds = (milliSeconds2 - milliSeconds1) / 1000;
        long elapsedDays = periodSeconds / 60 / 60 / 24;
        if ( x < elapsedDays && elapsedDays < x+1){
            return  true;
        }
        return false;
    }
}