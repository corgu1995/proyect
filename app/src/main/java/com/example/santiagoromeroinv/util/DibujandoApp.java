package com.example.santiagoromeroinv.util;

import android.app.Activity;

/**
 * Created by santiago.romero on 9/06/16.
 */
public class DibujandoApp {
    private Activity activity;
    private static DibujandoApp instance = null;

    public static DibujandoApp getInstance(){
        if (instance==null){
            instance = new DibujandoApp();
        }
        return instance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
