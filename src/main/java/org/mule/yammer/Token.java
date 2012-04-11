package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Token {

    @JsonProperty("view_members")
    Boolean viewMembers;
    @JsonProperty("expires_at")
    String expiresAt;
    @JsonProperty("created_at")
    String createdAt;
    @JsonProperty("view_groups")
    Boolean viewGroups;
    @JsonProperty("authorized_at")
    String authorizedAt;
    @JsonProperty("network_id")
    Long networkId;
    @JsonProperty("token")
    String token;
    @JsonProperty("network_permalink")
    String networkPermalink;
    @JsonProperty("user_id")
    Long userId;
    @JsonProperty("view_messages")
    Boolean viewMessages;
    @JsonProperty("modify_messages")
    Boolean modifyMessages;
    @JsonProperty("network_name")
    String networkName;
    @JsonProperty("modify_subscriptions")
    Boolean modifySubscriptions;
    @JsonProperty("view_subscriptions")
    Boolean viewSubscriptions;
    @JsonProperty("secret")
    String secret;
    @JsonProperty("view_tags")
    Boolean viewTags;

    public String getAuthorizedAt() {
        return authorizedAt;
    }

    public void setAuthorizedAt(String authorizedAt) {
        this.authorizedAt = authorizedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getModifyMessages() {
        return modifyMessages;
    }

    public void setModifyMessages(Boolean modifyMessages) {
        this.modifyMessages = modifyMessages;
    }

    public Boolean getModifySubscriptions() {
        return modifySubscriptions;
    }

    public void setModifySubscriptions(Boolean modifySubscriptions) {
        this.modifySubscriptions = modifySubscriptions;
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

    public String getNetworkPermalink() {
        return networkPermalink;
    }

    public void setNetworkPermalink(String networkPermalink) {
        this.networkPermalink = networkPermalink;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Boolean viewGroups) {
        this.viewGroups = viewGroups;
    }

    public Boolean getViewMembers() {
        return viewMembers;
    }

    public void setViewMembers(Boolean viewMembers) {
        this.viewMembers = viewMembers;
    }

    public Boolean getViewMessages() {
        return viewMessages;
    }

    public void setViewMessages(Boolean viewMessages) {
        this.viewMessages = viewMessages;
    }

    public Boolean getViewSubscriptions() {
        return viewSubscriptions;
    }

    public void setViewSubscriptions(Boolean viewSubscriptions) {
        this.viewSubscriptions = viewSubscriptions;
    }

    public Boolean getViewTags() {
        return viewTags;
    }

    public void setViewTags(Boolean viewTags) {
        this.viewTags = viewTags;
    }

}
