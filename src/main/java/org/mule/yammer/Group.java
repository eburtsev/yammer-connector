package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Group {

    @JsonProperty("type")
    String type;

    @JsonProperty("privacy")
    String privacy;

    @JsonProperty("web_url")
    String webUrl;

    @JsonProperty("mugshot_url")
    String mugshotUrl;

    @JsonProperty("url")
    String url;

    @JsonProperty("description")
    String description;

    @JsonProperty("full_name")
    String fullName;

    @JsonProperty("name")
    String name;

    @JsonProperty("id")
    String id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
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

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "Group{" + "type=" + type + ", privacy=" + privacy + ", webUrl=" + webUrl + ", mugshotUrl=" + mugshotUrl + ", url=" + url + ", description=" + description + ", fullName=" + fullName + ", name=" + name + ", id=" + id + '}';
    }

}
