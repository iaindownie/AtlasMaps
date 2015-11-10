package org.bto.atlasmaps.fluff;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.spawny.atlasmaps.R;

/**
 * Created by iaindownie on 19/10/2015.
 * Copyright @iaindownie
 * Excluded after discussion with Simon et al.
 */
public class CustomFamilyAdapter extends BaseAdapter {
    String[] a, b;
    private LayoutInflater layoutInflater;

    public CustomFamilyAdapter(Context aContext, String[] a, String[] b) {
        this.a = a;
        this.b = b;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return a.length;
    }

    @Override
    public Object getItem(int position) {
        return a[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.family_colour_row, null);
            holder = new ViewHolder();
            holder.group = (TextView) convertView.findViewById(R.id.family_name);
            holder.colour = convertView.findViewById(R.id.family_colour);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.group.setText(a[position]);
        holder.colour.setBackgroundColor(Color.parseColor("#" + b[position]));
        return convertView;
    }

    static class ViewHolder {
        TextView group;
        View colour;
    }
}