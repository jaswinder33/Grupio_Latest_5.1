package com.grupio.data;

public class MenuData {

	private String mMenyName="",mMenuOrder="",mDisplayName="";
	private String type="", menuIconUrl ="", settingID="";;
	
	
	public String getMenuName() {
		return mMenyName;
	}

	public void setMenuName(String menuName) {
		this.mMenyName = menuName;
	}

	public String getMenuOrder() {
		return mMenuOrder;
	}

	public void setMenuOrder(String menuorder) {
		this.mMenuOrder = menuorder;
	}

	public String getmDisplayName() {
		return mDisplayName;
	}

	public void setmDisplayName(String mDisplayName) {
		this.mDisplayName = mDisplayName;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuIconUrl() {
		return menuIconUrl;
	}

	public void setMenuIconUrl(String menuIconUrl) {
		this.menuIconUrl = menuIconUrl;
	}
	
	public String getSettingID() {
		return settingID;
	}

	public void setSettingID(String settingID) {
		this.settingID = settingID;
	}
	
	
}
