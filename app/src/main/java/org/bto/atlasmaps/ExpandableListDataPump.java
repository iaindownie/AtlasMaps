package org.bto.atlasmaps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
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
            return processMapSet(expandableListDetail);

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

            return processMapSet(expandableListDetail);
        }


    }

    /**
     * Method to process removed maps and re-order the remaining maps.
     * @param refinedArray
     * @return
     */
    private static LinkedHashMap processMapSet(LinkedHashMap refinedArray){
        LinkedHashMap newMap = new LinkedHashMap();
        ArrayList keys = new ArrayList(refinedArray.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String aKey = (String)keys.get(i);
            ArrayList values = (ArrayList)refinedArray.get(aKey);
            List newValues = new ArrayList();
            if(values.contains("Breeding Distribution 2008-11")){
                newValues.add("Breeding Distribution 2008-11");
            }
            if(values.contains("Winter Distribution 2007-11")){
                newValues.add("Winter Distribution 2007-11");
            }
            if(values.contains("Breeding Occupancy History 1970-2011")){
                newValues.add("Breeding Occupancy History 1970-2011");
            }
            if(values.contains("Winter Change 1980-2011")){
                newValues.add("Winter Change 1980-2011");
            }
            newMap.put(aKey, newValues);
        }
        System.out.println(newMap.toString());
        return newMap;
    }

    /*public static HashMap getGroupsParallel() {
        return groupsParallel;
    }*/
}
