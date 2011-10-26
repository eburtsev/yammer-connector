/**
 * Mule Yammer Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
n * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class Attachment
{
    private String type;
    @JsonProperty("content_type")
    private String contentType;
    private String uuid;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("inline_url")
    private String inlineUrl;
    @JsonProperty("inline_html")
    private String inlineHtml;
    @JsonProperty("y_id")
    private String yId;
    private ImageInfo image;
    private String name;
    private long id;

    private Ymodule ymodule;
    
    public Ymodule getYmodule() {
        return ymodule;
    }

    public void setYmodule(Ymodule ymodule) {
        this.ymodule = ymodule;
    }

    public String getInlineHtml() {
        return inlineHtml;
    }

    public void setInlineHtml(String inlineHtml) {
        this.inlineHtml = inlineHtml;
    }

    public String getInlineUrl() {
        return inlineUrl;
    }

    public void setInlineUrl(String inlineUrl) {
        this.inlineUrl = inlineUrl;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getWebUrl()
    {
        return webUrl;
    }

    public void setWebUrl(String webUrl)
    {
        this.webUrl = webUrl;
    }

    public String getyId()
    {
        return yId;
    }

    public void setyId(String yId)
    {
        this.yId = yId;
    }

    public ImageInfo getImage()
    {
        return image;
    }

    public void setImage(ImageInfo image)
    {
        this.image = image;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

}
