package com.crystal.getfps;

import android.app.Application;
import android.content.Context;

/**
 * Created by CrystalChen on 2017/9/7.
 */

public class SharedApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
