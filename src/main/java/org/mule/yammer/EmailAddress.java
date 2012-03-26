package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class EmailAddress {

	@JsonProperty("type")
	String type;

	@JsonProperty("address")
	String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
