package com.wgl.bored.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Biligle.
 */

public class MyApplication extends Application {

    public static List<Activity> list = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 完全退出
     */
    public static void exit(){
        if(list != null){
            for(Activity activity : list){
                activity.finish();
            }
            System.exit(0);
            list.clear();
        }
    }
}
