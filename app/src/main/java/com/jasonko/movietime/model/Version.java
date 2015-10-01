package com.jasonko.movietime.model;

/**
 * Created by kolichung on 9/30/15.
 */
public class Version {

    String versionName;
    String versionContent;
    int versionCode;

    public Version(String versionName, String versionContext, int versionCode) {
        this.versionName = versionName;
        this.versionContent = versionContext;
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public int getVersionCode() {
        return versionCode;
    }

}
