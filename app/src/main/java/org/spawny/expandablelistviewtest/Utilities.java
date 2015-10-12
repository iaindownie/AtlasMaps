package org.spawny.expandablelistviewtest;

/**
 * Created by iaindownie on 30/09/2015.
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
        if (aStr.contains("BD20072011_")) return "Breeding distribution 2007-11";
        else if (aStr.contains("WD20072011_")) return "Winter distribution 2007-11";
        else if (aStr.contains("BA20072011_")) return "Breeding abundance 2007-11";
        else if (aStr.contains("WA20072011_")) return "Winter abundance 2007-11";
        else if (aStr.contains("BD19681972_")) return "Breeding distribution 1968-72";
        else if (aStr.contains("WD19681972_")) return "Winter distribution 1968-72";
        else if (aStr.contains("BD19881991_")) return "Breeding distribution 1988-91";
        else if (aStr.contains("WD19811984_")) return "Winter distribution 1981-84";
        else if (aStr.contains("BC19701990_")) return "Breeding change 1970-90";
        else if (aStr.contains("BC19702011_")) return "Breeding change 1970-2011";
        else if (aStr.contains("BC19902011_")) return "Breeding change 1990-2011";
        else if (aStr.contains("WC19802011_")) return "Winter change 1980-2011";
        else if (aStr.contains("BH19702011_")) return "Breeding occupancy history 1970-2011";
        else if (aStr.contains("BT19902011_")) return "Breeding abundance change 1990-2011";
        else return aStr;
    }

}
