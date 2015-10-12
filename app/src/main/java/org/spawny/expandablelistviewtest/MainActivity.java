package org.spawny.expandablelistviewtest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import xmlwise.XmlParseException;

/**
 * Created by Iain Downie on 30/09/2015.
 */
public class MainActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    /*
    Added for search
     */
    private SearchView search;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;
    private int lastExpandedPosition = -1;

    PlistReader plr = new PlistReader();
    HashMap mapSet = null;
    TreeMap mapEnglishNamesAndSpeciesCodes = null;
    TreeMap mapSpeciesCodesAndEnglishNames = null;
    InputMethodManager imm;

    private int originalPosition = 0;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        try {
            mapSet = plr.getMapsAsHashMap(getApplicationContext(), false);
            mapEnglishNamesAndSpeciesCodes = plr.getSpeciesNamesAsTreeMap(getApplicationContext());
            mapSpeciesCodesAndEnglishNames = new TreeMap<>();
            ArrayList codedSpecies = new ArrayList<>(mapEnglishNamesAndSpeciesCodes.keySet());
            for (int i = 0; i < codedSpecies.size(); i++) {
                String speciesName = (String) codedSpecies.get(i);
                String speciesCode = (String) mapEnglishNamesAndSpeciesCodes.get(speciesName);
                mapSpeciesCodesAndEnglishNames.put(new Integer(speciesCode), speciesName);
            }
            expandableListDetail = ExpandableListDataPump.getData(this, mapEnglishNamesAndSpeciesCodes,
                    mapSpeciesCodesAndEnglishNames, mapSet);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlParseException ex) {
            ex.printStackTrace();
        }

        /*
    Added for search
     */
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
        /**/

        //display the list
        //displayList();
        //expand all Groups
        //expandAll();


        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded: " + groupPosition,
                        Toast.LENGTH_SHORT).show();*/
                //expandableListView.smoothScrollToPosition(50);
                //expandableListView.smoothScrollToPositionFromTop(groupPosition, 0);
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
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " " + childPosition + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                String speciesCode = (String) mapEnglishNamesAndSpeciesCodes.get(expandableListTitle.get(groupPosition));
                ArrayList aBunchOfMaps = (ArrayList) mapSet.get(Utilities.padWithNaughts(speciesCode));
                String mapName = (String) aBunchOfMaps.get(childPosition);
                //System.out.println(parent.getSelectedPosition() + " : " + groupPosition);
                //System.out.println("A:" + originalPosition);
                originalPosition = groupPosition;
                //System.out.println("B:" + originalPosition);
                Intent mapView = new Intent(getBaseContext(),
                        MapDetail.class);
                Bundle b = new Bundle();
                b.putString("MAP", mapName.substring(mapName.indexOf("=") + 1, mapName.length() - 1));
                b.putString("TITLE", expandableListTitle.get(groupPosition));
                mapView.putExtras(b);
                startActivity(mapView);
                return false;
            }
        });

        //expandAll();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Activity Life Cycle : onStart : Activity Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Activity Life Cycle : onResume : Activity Resumed");
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
        if (id == R.id.action_settings) {
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
        System.out.println("Calling close...");
        expandableListAdapter.filterData("");
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        //expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        expandableListAdapter.filterData(query);
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        //expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        expandableListAdapter.filterData(query);
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++)
            expandableListView.collapseGroup(i);
        search.clearFocus();
        //expandAll();
        return false;
    }

    /*private void expandAll() {
        int count = expandableListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }*/
}
