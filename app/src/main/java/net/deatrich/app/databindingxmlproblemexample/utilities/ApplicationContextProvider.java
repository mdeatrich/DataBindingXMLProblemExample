package net.deatrich.app.databindingxmlproblemexample.utilities;

import android.app.Application;
import android.content.Context;

// I found this on the web somewhere, I'll continue trying to find the source so I
// can credit

public class ApplicationContextProvider extends Application {

    private static final String TAG = ApplicationContextProvider.class.getSimpleName();

    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }



}
