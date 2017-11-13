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

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
public class parsePOI {

	private static parsePOI instance = null;



	private  Map<Location, Poi> pois = new HashMap<Location, Poi>();
	
	private parsePOI(){
		PoiList poiList =  new PoiList() ;
//	String fileName="C:\\Users\\avrahal\\Documents\\workspace\\Ackaton\\Sailent\\src\\main\\resorcess\\movie_theater.xml";
//	File file = new File(fileName);

    //    String yourFilePath = context.getFilesDir() + "/" + "hello.txt";

  //      File yourFile = new File( yourFilePath );


        URL website = null;
        try {
            website = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.513207,34.6033558&radius=500000&type=movie_theater&key=AIzaSyBAQeoCvI_wnoL1lmkOP1f7nuw009q5yYI");

        URLConnection dc = website.openConnection();
		dc.setConnectTimeout(5000);
		dc.setReadTimeout(5000);
		BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream()));
		 //    BufferedReader in = new BufferedReader(new FileReader(file));
      //  BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));
        //System.out.println(in.read());

       // JAXBContext jaxbContext = JAXBContext.newInstance(PoiList.class);
	//	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		//poiList = (PoiList) jaxbUnmarshaller.unmarshal(in);
        poiList = new Gson().fromJson(in ,PoiList.class);
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
