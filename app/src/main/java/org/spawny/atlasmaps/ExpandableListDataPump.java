package org.spawny.atlasmaps;

import android.content.Context;

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
public class ExpandableListDataPump {

    public static LinkedHashMap<String, List<String>> getData(Context context, TreeMap mapEnglishNamesAndSpeciesCodes, TreeMap mapSpeciesCodesAndEnglishNames, HashMap mapSet) throws IOException, XmlParseException {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();

        //System.out.println("mapEnglishNamesAndSpeciesCodes: " + mapEnglishNamesAndSpeciesCodes.size() + " " + mapEnglishNamesAndSpeciesCodes.toString());
        //System.out.println("mapSpeciesCodesAndEnglishNames: " + mapSpeciesCodesAndEnglishNames.size() + " " + mapSpeciesCodesAndEnglishNames.toString());
        //System.out.println("mapSet: " + mapSet.size() + " " + mapSet.toString());

        ArrayList alphaSpecies = new ArrayList(mapSpeciesCodesAndEnglishNames.keySet());

        for (int i = 0; i < alphaSpecies.size(); i++) {
            Integer speciesCode = (Integer) alphaSpecies.get(i);
            String speciesName = (String) mapSpeciesCodesAndEnglishNames.get(speciesCode);
            String paddedSpeciesCode = Utilities.padWithNaughts("" + speciesCode);
            List anArray = (ArrayList) mapSet.get(paddedSpeciesCode);
            List refinedArray = new ArrayList();
            for (int j = 0; j < anArray.size(); j++) {
                refinedArray.add(Utilities.getSensibleMapName((String) anArray.get(j)));
            }
            expandableListDetail.put(speciesName, refinedArray);
        }

        return expandableListDetail;
    }


}
