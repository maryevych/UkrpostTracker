package ua.pp.a_i.ukrpost_tracker.app.ukrposttracker.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yevhen on 20.03.14.
 */
public class ParcelsApp extends Application {
    private static Context context;

    public ParcelsApp() {
        context=getApplicationContext();
    }

    public Context getContext(){
        return context;
    }
}
