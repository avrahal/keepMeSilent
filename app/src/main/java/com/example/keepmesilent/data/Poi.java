package com.example.keepmesilent.data;

//import javax.xml.bind.annotation.XmlRootElement;
public class Poi {
	private String name;
	private String vicinity;
	private Geometry geometry;
	
	public Poi() {
		super();
	}
	
	public Poi(String name, String vicinity, Geometry geometry) {
		super();
		this.name = name;
		this.vicinity = vicinity;
		this.geometry = geometry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	

	
	
}
