package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class ImAddress {

	@JsonProperty("provider")
	String provider;

	@JsonProperty("username")
	String username;

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
