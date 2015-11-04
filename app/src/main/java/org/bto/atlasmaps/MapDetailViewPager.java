package org.bto.atlasmaps;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bto.atlasmaps.cortiz.TouchImageView;
import org.spawny.atlasmaps.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Iain Downie on 30/09/2015.
 * Updated ViewPager class to contain all the maps, make them zoomable
 * and swipe enabled. Also adds extra text and button option as overlay
 */
public class MapDetailViewPager extends Activity {

    private static final String TAG = "MapDetail";

    private ImageView mImageView;
    private TextView positionInArray;
    private InputStream ims;
    //private PhotoViewAttacher mAttacher;
    private int position = 0;
    private ArrayList allMaps;
    private ArrayList imageHolder;
    private String birdTrackPackageString = "bto.org.monitoring.birdtrack";


    ViewPager vPager = null;

    //int img[] = {R.drawable.right_arrow, R.drawable.splash_640_960};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view_pager);

        positionInArray = (TextView) findViewById(R.id.locEx);
        //String aMap;

        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        allMaps = (ArrayList) b.getSerializable("ALLMAPS");
        position = b.getInt("POSITION");
        String title = b.containsKey("TITLE") ? b.getString("TITLE") : "Unknown Species";
        setTitle(title);

        imageHolder = new ArrayList();

        for (int i = 0; i < allMaps.size(); i++) {
            try {
                ims = getAssets().open("maps/" + (String) allMaps.get(i));
                Drawable d = Drawable.createFromStream(ims, null);
                imageHolder.add(d);
            } catch (IOException ex) {
                return;
            }
        }


        vPager = (ViewPager) findViewById(R.id.viewpager);

        vPager.setAdapter(new MyAdapter());
        vPager.setCurrentItem(position);

        StringBuilder strBuild = new StringBuilder();
        strBuild.append((position + 1));
        strBuild.append("/");
        strBuild.append(allMaps.size());
        positionInArray.setText(strBuild);

        /**
         * Get the current vPager position from the ViewPager by
         * extending SimpleOnPageChangeListener class and updating the TextView
         */
        vPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            private int currentPage;

            @Override
            public void onPageSelected(int pos) {
                currentPage = pos;
                StringBuilder strBuild = new StringBuilder();
                strBuild.append((pos + 1));
                strBuild.append("/");
                strBuild.append(allMaps.size());
                positionInArray.setText(strBuild);
            }

            public final int getCurrentPage() {
                return currentPage;
            }
        });

        // Code requested as option/idea by Simon Gillings - direct link to BirdTrack
        // Extra code is to detect if BT installed on phone or not
        /*ImageButton birdTrackButton = (ImageButton) findViewById(R.id.birdTrackButton);
        boolean installed = appInstalledOrNot(birdTrackPackageString);
        if (installed) {
            // Add a standard click listener to the button
            birdTrackButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "BirdTrack button clicked", Toast.LENGTH_SHORT).show();
                    Intent LaunchIntent = getPackageManager()
                            .getLaunchIntentForPackage(birdTrackPackageString);
                    startActivity(LaunchIntent);
                }
            });
        } else {
            // Hide BirdTrack icon on maps
            birdTrackButton.setVisibility(View.GONE);
        }*/

    }

    /**
     * Method from StackOverflow to detect if a particular App is installed or not,
     * in this case BirdTrack - requires the package URI
     * http://stackoverflow.com/a/11392276/959481
     *
     * @param uri A typical package UIR, e.g. bto.org.monitoring.birdtrack
     * @return True or False
     */
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return 4;
            return allMaps.size();
            //return img.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int pos) {
            //int newPos = position;
            TouchImageView img = new TouchImageView(container.getContext());
            img.setImageDrawable((Drawable) imageHolder.get(pos));

            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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
