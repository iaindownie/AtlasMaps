package org.bto.atlasmaps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.spawny.atlasmaps.R;

import java.util.HashMap;
import java.util.List;

public class FamilyColours extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_colours);

        String title = "Species group colours";
        setTitle(title);

        List families = Utilities.bouGroupings();
        HashMap families2 = Utilities.bouGroupingsColours();
        String[] groups = (String[]) families.toArray(new String[families.size()]);
        String[] colours = new String[families.size()];
        for (int i = 0; i < families.size(); i++) {
            colours[i] = (String) families2.get((String) families.get(i));
        }

        final ListView lv1 = (ListView) findViewById(R.id.family_list);
        lv1.setAdapter(new CustomFamilyAdapter(this, groups, colours));
    }
}
