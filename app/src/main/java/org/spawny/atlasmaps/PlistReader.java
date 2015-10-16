package org.spawny.atlasmaps;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import xmlwise.Plist;
import xmlwise.XmlParseException;

/**
 * Created by Iain Downie on 30/09/2015.
 */
public class PlistReader {

    public HashMap getMapsAsHashMap(Context context, boolean sensibleNames) throws XmlParseException, IOException {
        HashMap<String, Object> hMap = null;
        HashMap<String, List> refinedMap = new HashMap<>();
        try {
            //In order to access files stored in Asset folder you need AssetManager
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                inputStream = assetManager.open("mapset.plist");
                //read it with BufferedReader
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                hMap = new HashMap(Plist.fromXml(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList alphaSpecies = new ArrayList(hMap.keySet());
        List anArray, species2;
        ArrayList b;
        for (int i = 0; i < alphaSpecies.size(); i++) {
            anArray = (ArrayList) hMap.get(alphaSpecies.get(i));
            species2 = new ArrayList<>();
            for (int j = 0; j < anArray.size(); j++) {
                b = (ArrayList) anArray.get(j);
                for (int z = 0; z < b.size(); z++) {
                    if (sensibleNames) {
                        species2.add(Utilities.getSensibleMapName(b.get(z).toString()));
                    } else {
                        species2.add(b.get(z).toString());
                    }

                }
            }
            refinedMap.put((String) alphaSpecies.get(i), species2);
        }
        return refinedMap;

    }

    public TreeMap getSpeciesNamesAsTreeMap(Context context) throws XmlParseException, IOException {
        TreeMap<String, Object> tMap = null;
        try {
            //In order to access files stored in Asset folder you need AssetManager
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                //inputStream = assetManager.open("species_atlas.plist");
                inputStream = assetManager.open("species.plist");
                //read it with BufferedReader
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                tMap = new TreeMap(Plist.fromXml(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tMap;

    }

    public LinkedHashMap getBouListAsHashMap(Context context) throws XmlParseException, IOException {
        HashMap<String, Object> hMap = null;
        try {
            //In order to access files stored in Asset folder you need AssetManager
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = null;
            BufferedReader br = null;
            try {
                //inputStream = assetManager.open("species_atlas.plist");
                inputStream = assetManager.open("bou_atlas_ordered.plist");
                //read it with BufferedReader
                br = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                hMap = new HashMap(Plist.fromXml(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        LinkedHashMap lhMap = new LinkedHashMap();
        ArrayList groupings = Utilities.bouGroupings();
        for(int i = 0; i < groupings.size(); i++){
            String aGroup = (String)groupings.get(i);
            if(hMap.containsKey(aGroup)){
                lhMap.put(aGroup, (List)hMap.get(aGroup));
            }
        }

        return lhMap;

    }


}