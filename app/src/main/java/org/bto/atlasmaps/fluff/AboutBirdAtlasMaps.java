package org.bto.atlasmaps.fluff;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import org.spawny.atlasmaps.R;

/**
 * Created by Iain Downie on 09/11/2015.
 * About page for App, includes links and email address
 * for help from BTO and other legal terms etc.
 */
public class AboutBirdAtlasMaps extends Activity {

    private TextView appEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_bird_atlas_maps);
        setTitle("About Mapstore App");

        TextView aboutText = (TextView) findViewById(R.id.about_text);
        StringBuilder mainText = new StringBuilder();
        mainText.append("The Bird Atlas Mapstore App presents breeding and winter season ");
        mainText.append("distribution and change maps for all species, where data are available, ");
        mainText.append("from the BTO/BirdWatch Ireland/SOC Bird Atlas 2007–11.");
        aboutText.setText(mainText.toString());

        TextView feedback = (TextView) findViewById(R.id.interpret_text);
        StringBuilder interpretText = new StringBuilder();
        interpretText.append("For further information on interpretation of these maps, please ");
        interpretText.append("visit the <a href=\"http://www.bto.org/volunteer-surveys/birdatlas\">");
        interpretText.append("Atlas pages on the BTO Website</a>.");
        feedback.setText(Html.fromHtml(interpretText.toString()));
        feedback.setMovementMethod(LinkMovementMethod.getInstance());

        TextView buy = (TextView) findViewById(R.id.buy_text);
        StringBuilder buyText = new StringBuilder();
        buyText.append("If you are interested in buying the book from which these maps were ");
        buyText.append("extracted, please visit the <a href=\"http://www.bto.org/shop/bird-atlas\">BTO shop</a>.");
        buy.setText(Html.fromHtml(buyText.toString()));
        buy.setMovementMethod(LinkMovementMethod.getInstance());

        TextView footer = (TextView) findViewById(R.id.about_footer);
        StringBuilder footerText = new StringBuilder();
        footerText.append("This app was developed by <strong>Iain Downie</strong> on behalf of the <strong>British Trust ");
        footerText.append("for Ornithology</strong>. For suggestions and feedback please contact ");
        footerText.append("<a href=\"mailto:is@bto.org\">is@bto.org</a>.");
        footer.setText(Html.fromHtml(footerText.toString()));
        footer.setMovementMethod(LinkMovementMethod.getInstance());

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
        //Log.i(TAG, "Activity Life Cycle : onStop : Activity Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "Activity Life Cycle : onDestroy : Activity Destroyed");
    }
}
