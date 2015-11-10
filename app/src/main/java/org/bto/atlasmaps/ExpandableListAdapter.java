package org.bto.atlasmaps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.spawny.atlasmaps.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Iain Downie on 30/09/2015.
 * Commented out code (groupsParallel) keeps the possibility of family colour scheme alive
 * which has been excluded after discussion with Simon et al.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;

    private ArrayList<String> originalList;
    //private HashMap<String, String> groupsParallel;

    /*public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 LinkedHashMap<String, List<String>> expandableListDetail, HashMap groupsParallel) {*/
    public ExpandableListAdapter(Context context, List<String> expandableListTitle,
                                 LinkedHashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;

        this.originalList = new ArrayList<>();
        this.originalList.addAll(expandableListTitle);
        //this.groupsParallel = groupsParallel;

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        //System.out.println(expandedListText);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.aListItem);
        if (expandedListText.startsWith("Breeding") || expandedListText.startsWith("Crossbill")) {
            linearLayout.setBackgroundColor(Color.parseColor("#FFCC99"));
        } else {
            linearLayout.setBackgroundColor(Color.parseColor("#99CCFF"));
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        /*String group = (String) this.groupsParallel.get(listTitle);
        TextView circleTextView = (TextView) convertView
                .findViewById(R.id.circleTitle);
        circleTextView.setTypeface(null, Typeface.BOLD);
        circleTextView.setText(group.substring(0,1));*/
        /*View groupTab = convertView.findViewById(R.id.group_tab);
        if (groupsParallel.size() > 0) {
            groupTab.setBackgroundColor(Color.parseColor("#" + Utilities.bouGroupingsColours().get(group)));
        }*/

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    /**
     * Method to add search filter
     *
     * @param query
     */

    public void filterData(String query) {

        query = query.toLowerCase();
        Log.v("ExpandableListAdapter", String.valueOf(expandableListTitle.size()));
        expandableListTitle.clear();

        if (query.isEmpty()) {
            expandableListTitle.addAll(originalList);
        } else {

            for (String species : originalList) {

                List<String> speciesList = expandableListTitle;
                ArrayList<String> newList = new ArrayList<>();
                if (species.toLowerCase().contains(query)) {
                    newList.add(species);
                }

                if (newList.size() > 0) {
                    expandableListTitle.add(species);
                }
            }
        }

        notifyDataSetChanged();

    }
}