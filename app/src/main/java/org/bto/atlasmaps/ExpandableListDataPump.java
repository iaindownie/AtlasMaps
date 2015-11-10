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
 * Commented out code (groupsParallel) keeps the possibility of family colour scheme alive
 * which has been excluded after discussion with Simon et al.
 */
public class ExpandableListDataPump {

    //public static HashMap groupsParallel = new HashMap();

    public static LinkedHashMap<String, List<String>> getData(TreeMap mapEnglishNamesAndSpeciesCodes,
                                                              HashMap mapSet,
                                                              LinkedHashMap lhMap,
                                                              boolean isBOU)
            throws IOException, XmlParseException {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();

        if (isBOU) {
            ArrayList groupings = new ArrayList(lhMap.keySet());
            ArrayList bouSpecies = new ArrayList();
            for (int i = 0; i < groupings.size(); i++) {
                String aGroup = (String) groupings.get(i);
                ArrayList aList = (ArrayList) lhMap.get(aGroup);
                for (int j = 0; j < aList.size(); j++) {
                    bouSpecies.add((String) aList.get(j));
                    //groupsParallel.put((String) aList.get(j), aGroup);
                }
            }

            for (int i = 0; i < bouSpecies.size(); i++) {
                String bouSpeciesName = (String) bouSpecies.get(i);
                String bouSpeciesCode = Utilities.padWithNaughts((String) mapEnglishNamesAndSpeciesCodes.get(bouSpeciesName));
                List anArray = (ArrayList) mapSet.get(bouSpeciesCode);
                List refinedArray = new ArrayList();
                for (int j = 0; j < anArray.size(); j++) {
                    String aChildItem = (String) anArray.get(j);
                    // If clause to remove old maps (prototype)
                    if (aChildItem.contains("BD20072011_") || aChildItem.contains("BH19702011_")
                            || aChildItem.contains("WC19802011_") || aChildItem.contains("WD20072011_")) {
                        refinedArray.add(Utilities.getSensibleMapName(aChildItem));
                    }
                }
                // Final output for if BOU ordered list
                expandableListDetail.put(bouSpeciesName, refinedArray);
            }
            return expandableListDetail;

        } else {
            TreeMap mapSpeciesCodesAndEnglishNames = new TreeMap<>();
            ArrayList codedSpecies = new ArrayList<>(mapEnglishNamesAndSpeciesCodes.keySet());
            for (int i = 0; i < codedSpecies.size(); i++) {
                String speciesName = (String) codedSpecies.get(i);
                String speciesCode = (String) mapEnglishNamesAndSpeciesCodes.get(speciesName);
                mapSpeciesCodesAndEnglishNames.put(new Integer(speciesCode), speciesName);
            }
            ArrayList aList = new ArrayList(mapEnglishNamesAndSpeciesCodes.keySet());
            for (int i = 0; i < aList.size(); i++) {
                String speciesName = (String) aList.get(i);
                String paddedSpeciesCode = Utilities.padWithNaughts((String) mapEnglishNamesAndSpeciesCodes.get(speciesName));
                List anArray = (ArrayList) mapSet.get(paddedSpeciesCode);
                List refinedArray = new ArrayList();
                for (int j = 0; j < anArray.size(); j++) {
                    String aChildItem = (String) anArray.get(j);
                    // If clause to remove old maps (prototype)
                    if (aChildItem.contains("BD20072011_") || aChildItem.contains("BH19702011_")
                            || aChildItem.contains("WC19802011_") || aChildItem.contains("WD20072011_")) {
                        refinedArray.add(Utilities.getSensibleMapName(aChildItem));
                    }
                }
                // Final output for if Alphabetic species list
                expandableListDetail.put(speciesName, refinedArray);
            }

            return expandableListDetail;
        }


    }

    /*public static HashMap getGroupsParallel() {
        return groupsParallel;
    }*/
}
