package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserStatistics {

	@JsonProperty("updates")
	Long updates;

	@JsonProperty("following")
	Long following;

	@JsonProperty("followers")
	Long followers;

	public Long getFollowers() {
		return followers;
	}

	public void setFollowers(Long followers) {
		this.followers = followers;
	}

	public Long getFollowing() {
		return following;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

	public Long getUpdates() {
		return updates;
	}

	public void setUpdates(Long updates) {
		this.updates = updates;
	}

}
