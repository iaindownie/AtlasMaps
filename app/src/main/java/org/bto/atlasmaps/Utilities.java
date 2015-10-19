package org.bto.atlasmaps;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static ArrayList<String> getImageStringsOnly(ArrayList aList) {
        ArrayList<String> newList = new ArrayList<String>();
        for (int i = 0; i < aList.size(); i++) {
            String mapName = (String) aList.get(i);
            newList.add(mapName.substring(mapName.indexOf("=") + 1, mapName.length() - 1));
        }
        return newList;
    }


    public static ArrayList<String> bouGroupings() {
        ArrayList<String> groupings = new ArrayList<String>();
        groupings.add("Wildfowl");
        groupings.add("Gamebirds");
        groupings.add("Divers");
        groupings.add("Seabirds");
        groupings.add("Waterbirds");
        groupings.add("Grebes");
        groupings.add("Raptors");
        groupings.add("Crakes and rails");
        groupings.add("Cranes and bustards");
        groupings.add("Waders");
        groupings.add("Skuas");
        groupings.add("Auks");
        groupings.add("Terns");
        groupings.add("Gulls");
        groupings.add("Pigeons");
        groupings.add("Turacos");
        groupings.add("Cuckoos");
        groupings.add("Owls and nightjars");
        groupings.add("Swifts");
        groupings.add("Kingfishers and allies");
        groupings.add("Woodpeckers");
        groupings.add("Falcons");
        groupings.add("Parrots and allies");
        groupings.add("Orioles and shrikes");
        groupings.add("Crows");
        groupings.add("Crests and tits");
        groupings.add("Larks");
        groupings.add("Hirundines");
        groupings.add("Cetti's Warbler and Long-tailed Tit");
        groupings.add("Warblers");
        groupings.add("Waxwing, Nuthatch, treecreepers and Wren");
        groupings.add("Starlings and thrushes");
        groupings.add("Flycatchers and chats");
        groupings.add("Dunnock, sparrows and estrilids");
        groupings.add("Wagtails and pipits");
        groupings.add("Finches");
        groupings.add("Buntings and New World sparrows");
        groupings.add("Icterids and Parulids");
        return groupings;
    }

    public static HashMap<String, String> bouGroupingsColours() {
        HashMap<String, String> groupings = new HashMap<String, String>();
        groupings.put("Wildfowl", "99CCBB");
        groupings.put("Gamebirds", "FFCC99");
        groupings.put("Divers", "66CCCC");
        groupings.put("Seabirds", "3399CC");
        groupings.put("Waterbirds", "99FFCC");
        groupings.put("Grebes", "66CCCC");
        groupings.put("Raptors", "996600");
        groupings.put("Crakes and rails", "6699CC");
        groupings.put("Cranes and bustards", "99FFCC");
        groupings.put("Waders", "6699CC");
        groupings.put("Skuas", "3333CC");
        groupings.put("Auks", "666699");
        groupings.put("Terns", "3333CC");
        groupings.put("Gulls", "3333CC");
        groupings.put("Pigeons", "FFCCCC");
        groupings.put("Turacos", "FFCCCC");
        groupings.put("Cuckoos", "FFCCCC");
        groupings.put("Owls and nightjars", "999999");
        groupings.put("Swifts", "339999");
        groupings.put("Kingfishers and allies", "339999");
        groupings.put("Woodpeckers", "336666");
        groupings.put("Falcons", "996600");
        groupings.put("Parrots and allies", "FF9966");
        groupings.put("Orioles and shrikes", "FF9966");
        groupings.put("Crows", "FF9966");
        groupings.put("Crests and tits", "FFFF66");
        groupings.put("Larks", "999933");
        groupings.put("Hirundines", "999933");
        groupings.put("Cetti's Warbler and Long-tailed Tit", "FFFF66");
        groupings.put("Warblers", "CCCC33");
        groupings.put("Waxwing, Nuthatch, treecreepers and Wren", "666600");
        groupings.put("Starlings and thrushes", "FF9966");
        groupings.put("Flycatchers and chats", "FFFF33");
        groupings.put("Dunnock, sparrows and estrilids", "666600");
        groupings.put("Wagtails and pipits", "999933");
        groupings.put("Finches", "CC3300");
        groupings.put("Buntings and New World sparrows", "CC6633");
        groupings.put("Icterids and Parulids", "CCCCCC");
        return groupings;
    }


}
