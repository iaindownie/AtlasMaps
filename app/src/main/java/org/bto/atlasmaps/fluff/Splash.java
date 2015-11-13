package org.bto.atlasmaps.fluff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import org.bto.atlasmaps.MainActivity;
import org.spawny.atlasmaps.R;

/**
 * Created by iaindownie on 19/10/2015.
 * Copyright @iaindownie
 * Simple spash screen activity. Runs a temp Runnable thread before redirects
 * to the mian activity, closing itself. Only appears when app is launched on
 *  device, not if the app is still running in background.
 */
public class Splash extends Activity {

    /**
     * Duration of wait - 1000ms = 1second
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1200;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}