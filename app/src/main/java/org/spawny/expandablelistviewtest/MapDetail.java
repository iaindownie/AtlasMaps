package org.spawny.expandablelistviewtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by iaindownie on 30/09/2015.
 */
public class MapDetail extends Activity {

    private static final String TAG = "MapDetail";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView mImageView = (ImageView) findViewById(R.id.mapView);
        PhotoViewAttacher mAttacher;
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();

        String aMap = b.containsKey("MAP") ? b.getString("MAP") : "blank.png";
        //System.out.println("AMap:" + aMap);
        if (aMap.length() == 0) aMap = "blank.png";

        String title = b.containsKey("TITLE") ? b.getString("TITLE") : "Unknown Species";

        setTitle(title);


        try {
            // get input stream
            InputStream ims = getAssets().open("maps/" + aMap);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImageView.setImageDrawable(d);
            mAttacher = new PhotoViewAttacher(mImageView);

        } catch (IOException ex) {
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Activity Life Cycle : onStart : Activity Started");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i(TAG, "Activity Life Cycle : onResume : Activity Resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.i(TAG, "Activity Life Cycle : onPause : Activity Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.i(TAG, "Activity Life Cycle : onStop : Activity Stoped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "Activity Life Cycle : onDestroy : Activity Destroyed");
    }
}
