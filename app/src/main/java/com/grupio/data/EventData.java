package com.grupio.data;

public class EventData {
	private String event_id = "", organization_id = "", event_name = "",
			start_date = "", end_date = "", hide_speaker_info = "", hide_attendee_info = "";

	private String apsn_org_id = "", description = "", imageURL = "",large_image = "", static_map_url="",
			address1 = "", address2 = "", city = "", state = "", country = "",
			zipcode = "", user_id = "", timezone = "", register_url = "",
			hashtag = "", apsn_host_name = "",
			apsn_admin_email = "",
			apsn_admin_password = "",
			lattitude = "",
			// backgroundImage="",
			longitude = "", myschedule_login_enabled = "",
			attendee_login_required = "", registration_required = "",
			notes_login_required = "", show_notes_button = "",
			color_theme = "",event_color="";

	private String locale;
	private String enable_activity_feed;
	private String allow_to_post_feed;



	String bm = "";

	String show_schedule_button = "";
	String show_myschedule_button = "";
	String survey_login_req = "";

	String hide_attendee_images = "";
	String hide_speaker_images = "";
	String hide_sponsor_images = "";
	String hide_exhibitor_images = "";
	
	String name_order ="",about_section_text="",social_section_text="",sort_order="";
	String force_delete;
	String force_upgrade;
	String ticketing_url   = "";
	String event_website_url = "";
	String web_logo_image = "" ;
	String venue = "" ;
	String gettimezone = "";

	private String aboutText;
	private String showTracks;
	private String appVersion;
	private String forceDelete;
	private String forceUpdate;

	
	String mycalendar_login_required="";
	String isModeratorAvailable;
	private String photo_gallery_moderator_enable;

	String show_attendee_sessions = "";

	public String getAboutText() {
		return aboutText;
	}

	public void setAboutText(String aboutText) {
		this.aboutText = aboutText;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getForceDelete() {
		return forceDelete;
	}

	public void setForceDelete(String forceDelete) {
		this.forceDelete = forceDelete;
	}

	public String getForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(String forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public String getShowTracks() {
		return showTracks;
	}

	public void setShowTracks(String showTracks) {
		this.showTracks = showTracks;
	}

	public String getPhoto_gallery_moderator_enable() {
		return isModeratorAvailable;
	}

	public void setPhoto_gallery_moderator_enable(String photo_gallery_moderator_enable) {
		this.photo_gallery_moderator_enable = photo_gallery_moderator_enable;
	}

	public String getEnable_activity_feed() {
		return enable_activity_feed;
	}

	public void setEnable_activity_feed(String enable_activity_feed) {
		this.enable_activity_feed = enable_activity_feed;
	}

	public String getAllow_to_post_feed() {
		return allow_to_post_feed;
	}

	public void setAllow_to_post_feed(String allow_to_post_feed) {
		this.allow_to_post_feed = allow_to_post_feed;
	}


	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

//	public void setIsModeratorAvailable(String isModeratorAvailable) {
//		this.isModeratorAvailable = isModeratorAvailable;
//	}

	public String loginRequired = "";

	public String getLoginRequired() {
		return loginRequired;
	}

	public void setLoginRequired(String loginRequired) {
		this.loginRequired = loginRequired;
	}

	public void setModeratorAvailable(String str){
		isModeratorAvailable =str;
	}

	public String isModeratorAvailable(){
		return isModeratorAvailable;
	}

	public String getMycalendar_login_required() {
		return mycalendar_login_required;
	}

	public void setMycalendar_login_required(String mycalendar_login_required) {
		this.mycalendar_login_required = mycalendar_login_required;
	}
	
	
	public String getGettimezone() {
		return gettimezone;
	}

	public void setGettimezone(String gettimezone) {
		this.gettimezone = gettimezone;
	}
	
	public String getHide_attendee_info() {
		return hide_attendee_info;
	}

	public void setHide_attendee_info(String hide_attendee_info) {
		this.hide_attendee_info = hide_attendee_info;
	}

	
	public String getHide_speaker_info() {
		return hide_speaker_info;
	}

	public void setHide_speaker_info(String hide_speaker_info) {
		this.hide_speaker_info = hide_speaker_info;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getWeb_logo_image() {
		return web_logo_image;
	}

	public void setWeb_logo_image(String web_logo_image) {
		this.web_logo_image = web_logo_image;
	}

	public String getEvent_website_url() {
		return event_website_url;
	}
	public void setEvent_website_url(String event_website_url) {
		this.event_website_url = event_website_url;
	}

	public String getStatic_map_url() {
		return static_map_url;
	}

	public void setStatic_map_url(String static_map_url) {
		this.static_map_url = static_map_url;
	}
	
	public String getTicketing_url() {
		return ticketing_url;
	}

	public void setTicketing_url(String ticketing_url) {
		this.ticketing_url = ticketing_url;
	}

	public String getForce_delete() {
		return forceDelete;
	}


	public String getForce_upgrade() {
		return forceUpdate;
	}


	public String getName_order() {
		return name_order;
	}

	public void setName_order(String name_order) {
		this.name_order = name_order;
	}
	
	public String getHide_speaker_images() {
		return hide_speaker_images;
	}

	public void setHide_speaker_images(String hide_speaker_images) {
		this.hide_speaker_images = hide_speaker_images;
	}

	public String getHide_sponsor_images() {
		return hide_sponsor_images;
	}

	public void setHide_sponsor_images(String hide_sponsor_images) {
		this.hide_sponsor_images = hide_sponsor_images;
	}

	public String getHide_exhibitor_images() {
		return hide_exhibitor_images;
	}

	public void setHide_exhibitor_images(String hide_exhibitor_images) {
		this.hide_exhibitor_images = hide_exhibitor_images;
	}

	public String getHide_attendee_images() {
		return hide_attendee_images;
	}

	public void setHide_attendee_images(String hide_attendee_images) {
		this.hide_attendee_images = hide_attendee_images;
	}

	public String getSurvey_login_req() {
		return survey_login_req;
	}

	public void setSurvey_login_req(String survey_login_req) {
		this.survey_login_req = survey_login_req;
	}

	public String getShow_myschedule_button() {
		return show_myschedule_button;
	}

	public void setShow_myschedule_button(String show_myschedule_button) {
		this.show_myschedule_button = show_myschedule_button;
	}

	public String getShow_schedule_button() {
		return show_schedule_button;
	}

	public void setShow_schedule_button(String show_schedule_button) {
		this.show_schedule_button = show_schedule_button;
	}



	public String getShow_attendee_sessions() {
		return show_attendee_sessions;
	}

	public void setShow_attendee_sessions(String show_attendee_sessions) {
		this.show_attendee_sessions = show_attendee_sessions;
	}


	public void setApsn_org_id(String apsn_org_id) {
		this.apsn_org_id = apsn_org_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getRegister_url() {
		return register_url;
	}

	public void setRegister_url(String register_url) {
		this.register_url = register_url;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}


	public void setApsn_host_name(String apsn_host_name) {
		this.apsn_host_name = apsn_host_name;
	}


	public void setApsn_admin_email(String apsn_admin_email) {
		this.apsn_admin_email = apsn_admin_email;
	}


	public void setApsn_admin_password(String apsn_admin_password) {
		this.apsn_admin_password = apsn_admin_password;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getMyschedule_login_enabled() {
		return myschedule_login_enabled;
	}

	public void setMyschedule_login_enabled(String myschedule_login_enabled) {
		this.myschedule_login_enabled = myschedule_login_enabled;
	}

	public String getAttendee_login_required() {
		return attendee_login_required;
	}

	public void setAttendee_login_required(String attendee_login_required) {
		this.attendee_login_required = attendee_login_required;
	}

	public String getRegistration_required() {
		return registration_required;
	}

	public void setRegistration_required(String registration_required) {
		this.registration_required = registration_required;
	}

	public String getNotes_login_required() {
		return notes_login_required;
	}

	public void setNotes_login_required(String notes_login_required) {
		this.notes_login_required = notes_login_required;
	}

	public String getShow_notes_button() {
		return show_notes_button;
	}

	public void setShow_notes_button(String show_notes_button) {
		this.show_notes_button = show_notes_button;
	}

	public String getColor_theme() {
		return color_theme;
	}

	public void setColor_theme(String color_theme) {
		this.color_theme = color_theme;
	}

	public String getEvent_color() {
		return event_color;
	}

	public void setEvent_color(String event_color) {
		this.event_color = event_color;
	}

	public String getLarge_image() {
		return large_image;
	}

	public void setLarge_image(String large_image) {
		this.large_image = large_image;
	}

	public String getAbout_section_text() {
		return about_section_text;
	}

	public void setAbout_section_text(String about_section_text) {
		this.about_section_text = about_section_text;
	}

	public String getSocial_section_text() {
		return social_section_text;
	}

	public void setSocial_section_text(String social_section_text) {
		this.social_section_text = social_section_text;
	}

	public String getSort_order() {
		return sort_order;
	}

	public void setSort_order(String sort_order) {
		this.sort_order = sort_order;
	}
	


	
	
	

	// public String getBakgroundImage() {
	// return backgroundImage;
	// }
	//
	// public void setBackgroundImage(String backgroundImage) {
	// this.backgroundImage = backgroundImage;
	// }
}
