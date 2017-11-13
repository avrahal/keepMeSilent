package com.example.keepmesilent.data;

import java.util.ArrayList;

//import javax.xml.bind.annotation.XmlRootElement;
//		import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement (name = "PlaceSearchResponse")
public class PoiList {
//	@XmlElement(name="result")
	private ArrayList<Poi> results =  new ArrayList() ;
	public ArrayList<Poi> getResults() {
		return results;
	}

	public void setResults(ArrayList<Poi> results) {
		this.results = results;
	}

	public PoiList(ArrayList<Poi> results) {
		super();
		this.results = results;
	}

	public PoiList() {
		super();
	}
}
