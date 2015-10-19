package org.bto.atlasmaps;

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

    public static HashMap groupsParallel = new HashMap();

    public static LinkedHashMap<String, List<String>> getData(TreeMap mapEnglishNamesAndSpeciesCodes,
                                                              HashMap mapSet,
                                                              LinkedHashMap lhMap)
            throws IOException, XmlParseException {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();


        ArrayList groupings = new ArrayList(lhMap.keySet());
        //System.out.println("Grouping:" + groupings.toString());
        ArrayList bouSpecies = new ArrayList();
        for (int i = 0; i < groupings.size(); i++) {
            String aGroup = (String)groupings.get(i);
            ArrayList aList = (ArrayList)lhMap.get(aGroup);
            for (int j = 0; j < aList.size(); j++) {
                bouSpecies.add((String)aList.get(j));
                groupsParallel.put((String) aList.get(j), aGroup);
            }
        }
        //System.out.println("bouSpecies:" + bouSpecies.size() + " " + bouSpecies.toString());

        for (int i = 0; i < bouSpecies.size(); i++) {
            String bouSpeciesName = (String)bouSpecies.get(i);
            String bouSpeciesCode = Utilities.padWithNaughts((String)mapEnglishNamesAndSpeciesCodes.get(bouSpeciesName));
            List anArray = (ArrayList) mapSet.get(bouSpeciesCode);
            List refinedArray = new ArrayList();
            for (int j = 0; j < anArray.size(); j++) {
                refinedArray.add(Utilities.getSensibleMapName((String) anArray.get(j)));
            }
            expandableListDetail.put(bouSpeciesName, refinedArray);
        }
        //System.out.println("groupsParallel:" + groupsParallel.size() + " " + groupsParallel.toString());
        return expandableListDetail;
    }

    public static HashMap getGroupsParallel() {
        return groupsParallel;
    }
}
