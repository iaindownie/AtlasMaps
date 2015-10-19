package org.bto.atlasmaps;

import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SearchView;
import android.widget.TextView;

import org.bto.atlasmaps.fluff.FamilyColours;
import org.spawny.atlasmaps.BuildConfig;
import org.spawny.atlasmaps.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import xmlwise.XmlParseException;

/**
 * Created by Iain Downie on 30/09/2015.
 * Main Activity to launch application, populate ListView
 * from PLIST reader and create parent/child elements
 */
public class MainActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private SearchView search;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private int lastExpandedPosition = -1;

    private PlistReader plr = new PlistReader();
    private HashMap mapSet = null;
    private TreeMap mapEnglishNamesAndSpeciesCodes = null;
    private InputMethodManager imm;

    private int originalPosition = 0;

    private static final String TAG = "MainActivity";

    private HashMap groupsParallel;
    private Context myContext;

    SharedPreferences prefs;
    boolean isBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        myContext = this.getApplicationContext();
        prefs = getPreferences(Context.MODE_PRIVATE);
        isBook = prefs.getBoolean("LIST", true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

        this.performDataPump(isBook);

        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                int offsetGroupPosition = groupPosition;
                if (offsetGroupPosition > 0) offsetGroupPosition -= 1;
                smoothScrollToPositionFromTop(expandableListView, offsetGroupPosition);
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            }

        });


        expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String speciesCode = (String) mapEnglishNamesAndSpeciesCodes.get(expandableListTitle.get(groupPosition));
                ArrayList aBunchOfMaps = (ArrayList) mapSet.get(Utilities.padWithNaughts(speciesCode));
                String mapName = (String) aBunchOfMaps.get(childPosition);
                originalPosition = groupPosition;
                Intent mapView = new Intent(getBaseContext(),
                        MapDetail.class);
                Bundle b = new Bundle();
                b.putString("MAP", mapName.substring(mapName.indexOf("=") + 1, mapName.length() - 1));
                b.putString("TITLE", expandableListTitle.get(groupPosition));
                b.putInt("POSITION", childPosition);
                b.putSerializable("ALLMAPS", Utilities.getImageStringsOnly(aBunchOfMaps));
                mapView.putExtras(b);
                startActivity(mapView);
                return false;
            }
        });
    }

    private void performDataPump(boolean isBook) {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        try {
            String mapSetFilename = Constants.mapSetFile;
            String speciesNamesFilename = Constants.bookSpeciesFile;
            mapEnglishNamesAndSpeciesCodes = plr.getSpeciesNamesAsTreeMap(myContext, speciesNamesFilename);
            mapSet = plr.getMapsAsHashMap(myContext, mapSetFilename);
            LinkedHashMap bouListingsAsLinkedHashMap = plr.getBouListAsHashMap(myContext, isBook);

            expandableListDetail = ExpandableListDataPump.getData(mapEnglishNamesAndSpeciesCodes,
                    mapSet, bouListingsAsLinkedHashMap);

            groupsParallel = ExpandableListDataPump.getGroupsParallel();

        } catch (IOException | XmlParseException e) {
            e.printStackTrace();
        }

        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail, groupsParallel);
        expandableListView.setAdapter(expandableListAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Log.i(TAG, "Activity Life Cycle : onStart : Activity Started");
    }

    @Override
    protected void onResume() {
        expandableListView.setFocusable(true);
        expandableListView.setFocusableInTouchMode(true);
        expandableListView.requestFocus();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            this.doAboutDialog();
            return true;
        }
        if (id == R.id.list_switch) {
            this.doToggleSwitch();
            return true;
        }
        if (id == R.id.families) {
            Intent colourScheme = new Intent(getBaseContext(),
                    FamilyColours.class);
            startActivity(colourScheme);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
        View child = getChildAtPosition(view, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null) && ((child.getTop() == 0) || ((child.getTop() > 0) && !view.canScrollVertically(1)))) {
            return;
        }

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.setSelection(position);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) {
            }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0);
            }
        });
    }


    public static View getChildAtPosition(final AdapterView view, final int position) {
        final int index = position - view.getFirstVisiblePosition();
        if ((index >= 0) && (index < view.getChildCount())) {
            return view.getChildAt(index);
        } else {
            return null;
        }
    }

    @Override
    public boolean onClose() {
        expandableListAdapter.filterData("");
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        expandableListAdapter.filterData(query);
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        expandableListAdapter.filterData(query);
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        search.clearFocus();
        return false;
    }

    private void doAboutDialog() {

        //dialog = new Dialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.about);
        dialog.setTitle("Bird Atlas 2007-11 v" + BuildConfig.VERSION_NAME);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Blurb about Bird Atlas 2007-11 app.\n");
        Button but = (Button) dialog.findViewById(R.id.dismissButton);
        dialog.show();
        but.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void doToggleSwitch() {

        //dialog = new Dialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.toggle_species);
        dialog.setTitle("Toggle the species lists");
        TextView text = (TextView) dialog.findViewById(R.id.text);
        StringBuilder spawny = new StringBuilder();
        if (isBook) {
            spawny.append("Currently viewing PUBLISHED species only\n\n");
        } else {
            spawny.append("Currently viewing ALL species\n\n");
        }
        spawny.append("The Bird Atlas 2007-11 book included 300? species accounts, ");
        spawny.append("but maps exist for over 500 species. You can toggle between ");
        spawny.append("the smaller (easier to navigate) list and the full list by ");
        spawny.append("using the 'switch lists' button below.\n\n");
        spawny.append("Your choice will be remembered until you next change it.\n");
        text.setText(spawny.toString());

        Button toggle = (Button) dialog.findViewById(R.id.toggle_species);
        Button dismiss = (Button) dialog.findViewById(R.id.dismissButton);
        dialog.show();
        toggle.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                if (isBook) {
                    isBook = false;
                    performDataPump(isBook);
                    ((BaseAdapter) expandableListView.getAdapter()).notifyDataSetChanged();
                    SharedPreferences.Editor editor = prefs.edit().putBoolean(
                            "LIST", isBook);
                    editor.apply();
                } else {
                    isBook = true;
                    performDataPump(isBook);
                    ((BaseAdapter) expandableListView.getAdapter()).notifyDataSetChanged();
                    SharedPreferences.Editor editor = prefs.edit().putBoolean(
                            "LIST", isBook);
                    editor.apply();
                }
                dialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
