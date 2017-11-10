package com.artolanggeng.purnamakertasindo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dimoproperty6 on 5/19/16.
 */
public class SessionHelper
{
    private static SessionHelper mSharedInstance = null;

    private static String CAMERA_FRAME_WIDTH = "V7E4P4A3z8443j3";
    private static String CAMERA_FRAME_HEIGHT = "M87828E138WL6h6";

    private Context ctx;
    private float cameraWidth;
    private float cameraHeight;

    public static SessionHelper getInstance(Context ctx) {
        if (mSharedInstance == null) {
            mSharedInstance = new SessionHelper(ctx);
        }
        return mSharedInstance;
    }

    /**
     * Private Constructor
     */
    private SessionHelper(Context ctx) {
        super();
        this.ctx = ctx;

        //this.mListenerList = new ArrayList<UserSessionListener>();
        //userWidthPixel = -1;
        cameraWidth = -1;
        cameraHeight = -1;
        //newCredentials = new HashMap<String, String>();
        //urls = new ArrayList<String>();
    }

    public float getFrameWidth() {
        if (cameraWidth == -1) {
            this.cameraWidth = PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(CAMERA_FRAME_WIDTH, -1.0f);
        }

        return cameraWidth;
    }

    public void setFrameWidth(float width) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        editor.putFloat(CAMERA_FRAME_WIDTH, width);
        editor.apply();
        this.cameraWidth = width;
    }

    public float getFrameHeight() {
        if (cameraHeight == -1) {
            this.cameraHeight = PreferenceManager.getDefaultSharedPreferences(ctx).getFloat(CAMERA_FRAME_HEIGHT, -1.0f);
        }

        return cameraHeight;
    }

    public void setFrameHeight(float height) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        editor.putFloat(CAMERA_FRAME_HEIGHT, height);
        editor.apply();
        this.cameraHeight = height;
    }
}
