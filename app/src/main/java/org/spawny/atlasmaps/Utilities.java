package org.spawny.atlasmaps;

import java.util.ArrayList;

/**
 * Created by Iain Downie on 30/09/2015.
 */
public class Utilities {

    public static String padWithNaughts(String aStr) {
        if (aStr.length() == 1) return "0000" + aStr;
        else if (aStr.length() == 2) return "000" + aStr;
        else if (aStr.length() == 3) return "00" + aStr;
        else if (aStr.length() == 4) return "0" + aStr;
        else return aStr;
    }

    public static String removeNaughts(String aStr) {
        if (aStr.length() == 1) return aStr.substring(4);
        else if (aStr.length() == 2) return aStr.substring(3);
        else if (aStr.length() == 3) return aStr.substring(2);
        else if (aStr.length() == 4) return aStr.substring(1);
        else return aStr;
    }

    public static String getSensibleMapName(String aStr) {
        if (aStr.contains("BD20072011_")) return "Breeding Distribution 2008-11";
        else if (aStr.contains("WD20072011_")) return "Winter Distribution 2007-11";
        else if (aStr.contains("BA20072011_")) return "Breeding Abundance 2008-11";
        else if (aStr.contains("WA20072011_")) return "Winter Abundance 2008-11";
        else if (aStr.contains("BD19681972_")) return "Breeding Distribution 1968-72";
        else if (aStr.contains("WD19681972_")) return "Winter Distribution 1968-72";
        else if (aStr.contains("BD19881991_")) return "Breeding Distribution 1988-91";
        else if (aStr.contains("WD19811984_")) return "Winter Distribution 1981-84";
        else if (aStr.contains("BC19701990_")) return "Breeding Change 1970-90";
        else if (aStr.contains("BC19702011_")) return "Breeding Change 1970-2011";
        else if (aStr.contains("BC19902011_")) return "Breeding Change 1990-2011";
        else if (aStr.contains("WC19802011_")) return "Winter Change 1980-2011";
        else if (aStr.contains("BH19702011_")) return "Breeding Occupancy History 1970-2011";
        else if (aStr.contains("BT19902011_")) return "Breeding Abundance Change 1990-2011";
        else return aStr;
    }

    public static ArrayList getImageStringsOnly(ArrayList aList) {
        ArrayList newList = new ArrayList<>();
        for (int i = 0; i < aList.size(); i++) {
            String mapName = (String) aList.get(i);
            newList.add(mapName.substring(mapName.indexOf("=") + 1, mapName.length() - 1));
        }
        return newList;
    }

}
