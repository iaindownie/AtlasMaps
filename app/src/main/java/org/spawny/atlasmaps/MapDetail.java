package org.spawny.atlasmaps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Iain Downie on 30/09/2015.
 */
public class MapDetail extends Activity {

    private static final String TAG = "MapDetail";

    private Button leftButton;
    private Button rightButton;
    private ImageView mImageView;
    private TextView positionInArray;
    private InputStream ims;
    private PhotoViewAttacher mAttacher;
    private int position = 0;
    private ArrayList allMaps;
    private String aMap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView) findViewById(R.id.mapView);
        leftButton = (Button) findViewById(R.id.leftbutton);
        rightButton = (Button) findViewById(R.id.rightbutton);
        positionInArray = (TextView) findViewById(R.id.locEx);


        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        aMap = b.containsKey("MAP") ? b.getString("MAP") : "blank.png";
        allMaps = (ArrayList) b.getSerializable("ALLMAPS");
        position = b.getInt("POSITION");
        //System.out.println("AllMaps:" + allMaps.toString());
        //System.out.println("AMap:" + aMap);
        if (aMap.length() == 0) aMap = "blank.png";
        String title = b.containsKey("TITLE") ? b.getString("TITLE") : "Unknown Species";
        setTitle(title);

        try {
            // get input stream
            ims = getAssets().open("maps/" + aMap);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImageView.setImageDrawable(d);
            mAttacher = new PhotoViewAttacher(mImageView);
            positionInArray.setText((position + 1) + "/" + allMaps.size());

        } catch (IOException ex) {
            return;
        }

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.i(TAG, "Button Press : LeftButton");
                try {
                    // get input stream
                    if (position == 0) {
                        position = allMaps.size() - 1;
                    } else {
                        position -= 1;
                    }
                    //System.out.println("Position:" + position);
                    ims = getAssets().open("maps/" + (String) allMaps.get(position));

                    positionInArray.setText((position + 1) + "/" + allMaps.size());

                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to ImageView
                    mImageView.setImageDrawable(d);
                    mAttacher = new PhotoViewAttacher(mImageView);

                } catch (IOException ex) {
                    return;
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.i(TAG, "Button Press : RightButton");
                try {
                    // get input stream
                    if (position == allMaps.size() - 1) {
                        position = 0;
                    } else {
                        position += 1;
                    }
                    //System.out.println("Position:" + position);
                    ims = getAssets().open("maps/" + (String) allMaps.get(position));

                    positionInArray.setText((position + 1) + "/" + allMaps.size());

                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to ImageView
                    mImageView.setImageDrawable(d);
                    mAttacher = new PhotoViewAttacher(mImageView);

                } catch (IOException ex) {
                    return;
                }
            }
        });


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
        //Log.i(TAG, "Activity Life Cycle : onStart : Activity Started");

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
