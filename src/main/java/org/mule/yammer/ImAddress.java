package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class ImAddress {

	@JsonProperty("provider")
	String provider = "";

	@JsonProperty("username")
	String username = "";

    public ImAddress() {
        
    }

    public ImAddress(String provider, String username) {
        this();
        this.provider = provider;
        this.username = username;
    }
    
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

    @Override
    public String toString() {
        return "ImAddress{" + "provider=" + provider + ", username=" + username + '}';
    }

}
