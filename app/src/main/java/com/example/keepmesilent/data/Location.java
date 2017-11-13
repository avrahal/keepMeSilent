package com.example.keepmesilent.data;

//import javax.xml.bind.annotation.XmlRootElement;
//@XmlRootElement (name = "geometry")
public class Location implements Comparable<Object> {

	private double lat;
	private double lng;

	public Location() {
		super();
	}

	public Location(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	 @Override
	public boolean  equals(Object arg0) {
	//	System.out.println("Marco");
	//	System.out.println("lat = "+ Math.abs(this.lat - ((Location)arg0).lat));
	//	System.out.println("lng = "+ Math.abs(this.lng - ((Location)arg0).lng));
		 if ( Math.abs(this.lat - ((Location)arg0).lat) <= 0.00005 && 
		      Math.abs(this.lng - ((Location)arg0).lng) <= 0.00005 ) {
			return true;
		} else {
			return false;
		}
	}

	 @Override
	    public int hashCode() {
			//System.out.println("Marco22");
	       // return Double.toString(lat+lng).hashCode();
			return 1;
	    }
	 
	public int compareTo(Object arg0) {
		 if ( Math.abs(this.lat - ((Location)arg0).lat) <= 0.00005 && 
			      Math.abs(this.lng - ((Location)arg0).lng) <= 0.00005 ) {
				return 0;
			} else {
				return 1;
			}
	}

}
