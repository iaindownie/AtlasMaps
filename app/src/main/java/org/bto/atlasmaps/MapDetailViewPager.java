package org.bto.atlasmaps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.bto.atlasmaps.cortiz.TouchImageView;
import org.spawny.atlasmaps.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//import uk.co.senab.photoview.PhotoViewAttacher;

public class MapDetailViewPager extends Activity {

    private static final String TAG = "MapDetail";

    private ImageView mImageView;
    private TextView positionInArray;
    private InputStream ims;
    //private PhotoViewAttacher mAttacher;
    private int position = 0;
    private ArrayList allMaps;
    private ArrayList imageHolder;

    ViewPager view = null;

    int img[] = {R.drawable.right_arrow, R.drawable.splash_640_960};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view_pager);

        positionInArray = (TextView) findViewById(R.id.locEx);
        String aMap;

        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        aMap = b.containsKey("MAP") ? b.getString("MAP") : "blank.png";
        allMaps = (ArrayList) b.getSerializable("ALLMAPS");
        //System.out.println("AllMaps:" + allMaps.toString());
        position = b.getInt("POSITION");
        if (aMap.length() == 0) aMap = "blank.png";
        String title = b.containsKey("TITLE") ? b.getString("TITLE") : "Unknown Species";
        setTitle(title);

        imageHolder = new ArrayList();

        for (int i = 0; i < allMaps.size(); i++) {
            try {
                // get input stream from assets folder
                ims = getAssets().open("maps/" + (String) allMaps.get(i));
                //System.out.println("ims:" + ims.getClass());
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                //mImageView.setImageDrawable(d);
                // add to PhotoViewAttacher
                //mAttacher = new PhotoViewAttacher(mImageView);
                imageHolder.add(d);

            } catch (IOException ex) {
                return;
            }
        }


        view = (ViewPager) findViewById(R.id.viewpager);

        view.setAdapter(new MyAdapter());
        view.setCurrentItem(position);
        positionInArray.setText("Some text");
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return 4;
            return allMaps.size();
            //return img.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView img = new TouchImageView(container.getContext());
            img.setImageDrawable((Drawable) imageHolder.get(position));
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
