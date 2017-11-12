package com.example.keepmesilent.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement (name = "PlaceSearchResponse")
public class PoiList {
	@XmlElement(name="result")
	private ArrayList<Poi> poiLst =  new ArrayList() ;
	public ArrayList<Poi> getList() {
		return poiLst;
	}

	public void setList(ArrayList<Poi> list) {
		this.poiLst = list;
	}

	public PoiList(ArrayList<Poi> list) {
		super();
		this.poiLst = list;
	}
	
	public PoiList() {
		super();
	}
}
