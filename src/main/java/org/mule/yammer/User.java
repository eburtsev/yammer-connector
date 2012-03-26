package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	@JsonProperty("schools")
	String[] schools;

	@JsonProperty("kids_names")
	String kidsNames;

	@JsonProperty("type")
	String type;

	@JsonProperty("previous_companies")
	String[] previousCompanies;

	@JsonProperty("verified_admin")
	boolean verifiedAdmin;

	@JsonProperty("external_urls")
	String[] externalUrls;

	@JsonProperty("network_name")
	String networkName;

	@JsonProperty("timezone")
	String timezone;

	@JsonProperty("expertise")
	String expertise;

	@JsonProperty("network_id")
	Long networkId;

	@JsonProperty("stats")
	UserStatistics stats;

	@JsonProperty("url")
	String url;

	@JsonProperty("interests")
	String[] interests;

	@JsonProperty("location")
	String location;

	@JsonProperty("job_title")
	String jobTitle;

	@JsonProperty("mugshot_url")
	String mugshotUrl;

	@JsonProperty("birth_date")
	String birthdate;

	@JsonProperty("significant_other")
	String significantOther;

	@JsonProperty("full_name")
	String fullName;

	@JsonProperty("guid")
	String guid;

	@JsonProperty("network_domains")
	String[] networkDomains;

	@JsonProperty("summary")
	String summary;

	@JsonProperty("state")
	String state;

	@JsonProperty("hire_date")
	String hireDate;

	@JsonProperty("name")
	String name;

	@JsonProperty("web_url")
	String webUrl;

	@JsonProperty("can_broadcast")
	boolean canBroadcast;

	@JsonProperty("id")
	Long id;

	@JsonProperty("contact")
	UserContacts contact;

	@JsonProperty("admin")
	boolean isAdmin;

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public boolean isCanBroadcast() {
		return canBroadcast;
	}

	public void setCanBroadcast(boolean canBroadcast) {
		this.canBroadcast = canBroadcast;
	}

	public UserContacts getContact() {
		return contact;
	}

	public void setContact(UserContacts contact) {
		this.contact = contact;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String[] getExternalUrls() {
		return externalUrls;
	}

	public void setExternalUrls(String[] externalUrls) {
		this.externalUrls = externalUrls;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public boolean isIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getKidsNames() {
		return kidsNames;
	}

	public void setKidsNames(String kidsNames) {
		this.kidsNames = kidsNames;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMugshotUrl() {
		return mugshotUrl;
	}

	public void setMugshotUrl(String mugshotUrl) {
		this.mugshotUrl = mugshotUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getNetworkDomains() {
		return networkDomains;
	}

	public void setNetworkDomains(String[] networkDomains) {
		this.networkDomains = networkDomains;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String[] getPreviousCompanies() {
		return previousCompanies;
	}

	public void setPreviousCompanies(String[] previousCompanies) {
		this.previousCompanies = previousCompanies;
	}

	public String[] getSchools() {
		return schools;
	}

	public void setSchools(String[] schools) {
		this.schools = schools;
	}

	public String getSignificantOther() {
		return significantOther;
	}

	public void setSignificantOther(String significantOther) {
		this.significantOther = significantOther;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UserStatistics getStats() {
		return stats;
	}

	public void setStats(UserStatistics stats) {
		this.stats = stats;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isVerifiedAdmin() {
		return verifiedAdmin;
	}

	public void setVerifiedAdmin(boolean verifiedAdmin) {
		this.verifiedAdmin = verifiedAdmin;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

}
