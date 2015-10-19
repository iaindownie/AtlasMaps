package org.bto.atlasmaps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.spawny.atlasmaps.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Iain Downie on 30/09/2015.
 */
public class MapDetail extends Activity {

    private static final String TAG = "MapDetail";

    private ImageView mImageView;
    private TextView positionInArray;
    private InputStream ims;
    private PhotoViewAttacher mAttacher;
    private int position = 0;
    private ArrayList allMaps;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        Button leftButton;
        Button rightButton;
        String aMap;

        mImageView = (ImageView) findViewById(R.id.mapView);
        leftButton = (Button) findViewById(R.id.leftbutton);
        rightButton = (Button) findViewById(R.id.rightbutton);
        positionInArray = (TextView) findViewById(R.id.locEx);


        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        aMap = b.containsKey("MAP") ? b.getString("MAP") : "blank.png";
        allMaps = (ArrayList) b.getSerializable("ALLMAPS");
        position = b.getInt("POSITION");
        if (aMap.length() == 0) aMap = "blank.png";
        String title = b.containsKey("TITLE") ? b.getString("TITLE") : "Unknown Species";
        setTitle(title);

        if(allMaps.size()<2){
            leftButton.setVisibility(View.GONE);
            rightButton.setVisibility(View.GONE);
        }

        try {
            // get input stream from assets folder
            ims = getAssets().open("maps/" + aMap);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImageView.setImageDrawable(d);
            // add to PhotoViewAttacher
            mAttacher = new PhotoViewAttacher(mImageView);
            StringBuilder strBuild = new StringBuilder();
            strBuild.append((position + 1));
            strBuild.append("/");
            strBuild.append(allMaps.size());
            positionInArray.setText(strBuild);

        } catch (IOException ex) {
            return;
        }

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.i(TAG, "Button Press : LeftButton");
                try {
                    // get input stream from assets folder
                    if (position == 0) {
                        position = allMaps.size() - 1;
                    } else {
                        position -= 1;
                    }
                    ims = getAssets().open("maps/" + (String) allMaps.get(position));

                    StringBuilder strBuild = new StringBuilder();
                    strBuild.append((position + 1));
                    strBuild.append("/");
                    strBuild.append(allMaps.size());
                    positionInArray.setText(strBuild);

                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to ImageView
                    mImageView.setImageDrawable(d);
                    // add to PhotoViewAttacher
                    mAttacher = new PhotoViewAttacher(mImageView);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.i(TAG, "Button Press : RightButton");
                try {
                    // get input stream from assets folder
                    if (position == allMaps.size() - 1) {
                        position = 0;
                    } else {
                        position += 1;
                    }
                    ims = getAssets().open("maps/" + (String) allMaps.get(position));

                    StringBuilder strBuild = new StringBuilder();
                    strBuild.append((position + 1));
                    strBuild.append("/");
                    strBuild.append(allMaps.size());
                    positionInArray.setText(strBuild);

                    // load image as Drawable
                    Drawable d = Drawable.createFromStream(ims, null);
                    // set image to ImageView
                    mImageView.setImageDrawable(d);
                    // add to PhotoViewAttacher
                    mAttacher = new PhotoViewAttacher(mImageView);

                } catch (IOException ex) {
                    ex.printStackTrace();
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
