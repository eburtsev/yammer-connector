package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserContacts {

	@JsonProperty("email_addresses")
	EmailAddress[] emailAddresses;

	@JsonProperty("im")
	ImAddress imAddress;

	@JsonProperty("phone_numbers")
	String[] phoneNumbers;

	public EmailAddress[] getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(EmailAddress[] emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public ImAddress getImAddress() {
		return imAddress;
	}

	public void setImAddress(ImAddress imAddress) {
		this.imAddress = imAddress;
	}

	public String[] getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String[] phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

}
