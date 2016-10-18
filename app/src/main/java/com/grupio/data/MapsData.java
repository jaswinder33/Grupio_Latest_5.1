package com.grupio.data;

public class MapsData {

	private String mapId="";
	private String name="";
	private String url = "";
	public String Interactive = "";

	public String order="";

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getInteractive() {
		return Interactive;
	}

	public void setInteractive(String interactive) {
		Interactive = interactive;
	}

	public String getMapId() {
		return mapId;
	}

	public void setMapId(String mapId) {
		this.mapId = mapId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
