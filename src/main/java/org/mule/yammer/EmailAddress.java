package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EmailAddress {

	@JsonProperty("type")
	String type;

	@JsonProperty("address")
	String address;

    public EmailAddress() {
    }

    public EmailAddress(String type, String address) {
        this.address = address;
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "EmailAddress{" + "type=" + type + ", address=" + address + '}';
    }

}
