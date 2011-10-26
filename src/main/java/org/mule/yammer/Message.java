/**
 * Mule Yammer Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.yammer;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Message {

    @JsonProperty("client_url")
    private String clientUrl;

    @JsonProperty("system_message")
    private String systemMessage;

    private Body body;

    @JsonProperty("sender_type")
    private String senderType;

    @JsonProperty("network_id")
    private long networkId;

    @JsonProperty("thread_id")
    private long threadId;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("direct_message")
    private boolean directMessage;

    private long id;

    private String url;

    @JsonProperty("client_type")
    private String clientType;

    @JsonProperty("message_type")
    private String messageType;

    @JsonProperty("sender_id")
    private long senderId;

    @JsonProperty("replied_to_id")
    private long repliedToId;

    private List<Attachment> attachments;

    @JsonProperty("liked_by")
    private LikedBy likedBy;

    private String privacy;

    @JsonProperty("created_at")
    private String createdAt;

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(long networkId) {
        this.networkId = networkId;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public boolean isDirectMessage() {
        return directMessage;
    }

    public void setDirectMessage(boolean directMessage) {
        this.directMessage = directMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRepliedToId() {
        return repliedToId;
    }

    public void setRepliedToId(long repliedToId) {
        this.repliedToId = repliedToId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public LikedBy getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(LikedBy likedBy) {
        this.likedBy = likedBy;
    }

}
