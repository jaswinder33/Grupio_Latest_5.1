package com.grupio.data;


import java.io.Serializable;

public class mapList implements Serializable, DocInter{

	
	String mapId="";
	String mapName="";
	String mapUrl="";
	String mapType="";
	public String mapInteractive = "";
	
	String loginRequired = "";
	
	public String getLoginRequired() {
		return loginRequired;
	}
	public void setLoginRequired(String loginRequired) {
		this.loginRequired = loginRequired;
	}
	
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getMapName() {
		return mapName;
	}
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public String getMapInteractive() {
		return mapInteractive;
	}
	public void setMapInteractive(String mapInteractive) {
		this.mapInteractive = mapInteractive;
	}
	

	
}


