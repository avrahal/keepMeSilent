package com.example.keepmesilent;

import com.example.keepmesilent.data.Location;
import com.example.keepmesilent.data.Poi;
import com.example.keepmesilent.data.PoiList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


public class parsePOI {

	private static parsePOI instance = null;
	private  Map<Location, Poi> pois = new HashMap<Location, Poi>();



    public void updatePOI (String POITypes,com.example.keepmesilent.data.Location location ) {
        //"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.513207,34.6033558&radius=500000&type=movie_theater&key=AIzaSyBAQeoCvI_wnoL1lmkOP1f7nuw009q5yYI"
        String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String radious = "radius=5000000";
        String key = "key=AIzaSyBAQeoCvI_wnoL1lmkOP1f7nuw009q5yYI";
        String uri;

        if (POITypes.contains("movie_theater")) {
            uri = URL + location.getLat() + "," + location.getLng() + "&" + radious + "&type=movie_theater&" + key;
            UpdatePois(uri);
        }
        if (POITypes.contains("museum")) {
            uri = URL + location.getLat() + "," + location.getLng() + "&" + radious + "&type=museum&" + key;
            UpdatePois(uri);
        }
        if (POITypes.contains("church")) {
            uri = URL + location.getLat() + "," + location.getLng() + "&" + radious + "&type=church&" + key;
            UpdatePois(uri);
        }
        if (POITypes.contains("synagogue")) {
            uri = URL + location.getLat() + "," + location.getLng() + "&" + radious + "&type=synagogue&" + key;
            UpdatePois(uri);
        }

    }
  private void   UpdatePois(String url){
        java.net.URL website = null;
        System.out.println(url);
      PoiList poiList = null;
        try {
            website = new URL(url);

            URLConnection dc = website.openConnection();
            dc.setConnectTimeout(5000);
            dc.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream()));
            poiList=new Gson().fromJson(in ,PoiList.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Poi p : poiList.getResults())
        {
            pois.put(p.getGeometry().getLocation(), p);
        }


    }


	private parsePOI(){
	}

	public  Poi getPoi(Location loc) {
		return pois.get(loc);
	}
	
	public Map<Location, Poi> getPois() {
		return pois;
	}
	
	public static parsePOI getInstance()  {
		if (instance==null)
		{
			instance= new parsePOI();
		}
		return instance;
	}

	
}
