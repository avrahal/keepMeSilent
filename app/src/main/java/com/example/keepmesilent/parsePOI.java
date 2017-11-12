package com.example.keepmesilent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.example.keepmesilent.data.*;
import java.util.Map;
import java.util.HashMap;

public class parsePOI {

	private static parsePOI instance = null;



	private  Map<Location, Poi> pois = new HashMap<Location, Poi>();
	
	private parsePOI(){
		PoiList poiList =  new PoiList() ;
	//String fileName="C:\\Users\\avrahal\\Documents\\workspace\\Ackaton\\Sailent\\src\\main\\resorcess\\movie_theater.xml";
	//File file = new File(fileName);
	

	
	
	try {
		URL website = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=31.513207,34.6033558&radius=500000&type=movie_theater&key=AIzaSyBAQeoCvI_wnoL1lmkOP1f7nuw009q5yYI");
		BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
		JAXBContext jaxbContext = JAXBContext.newInstance(PoiList.class);
		Unmarshaller jaxbUnmarshaller;
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		poiList = (PoiList) jaxbUnmarshaller.unmarshal(in);
	} catch (JAXBException e) {

		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	for (Poi p : poiList.getList())
	{
		pois.put(p.getGeometry().getLocation(), p);
	}
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
