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

public class Body
{
    private String parsed;
    private String plain;
    private List<String> urls;
    
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    public String getParsed()
    {
        return parsed;
    }
    public void setParsed(String parsed)
    {
        this.parsed = parsed;
    }
    public String getPlain()
    {
        return plain;
    }
    public void setPlain(String plain)
    {
        this.plain = plain;
    }

    @Override
    public String toString() {
        return "Body{" +
                "parsed='" + parsed + '\'' +
                ", plain='" + plain + '\'' +
                ", urls=" + urls +
                '}';
    }
}
