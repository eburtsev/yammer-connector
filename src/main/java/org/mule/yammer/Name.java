/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.yammer;

import org.codehaus.jackson.annotate.JsonProperty;

public class Name
{
    private String permalink;
    @JsonProperty("full_name")
    private String fullName;
    public String getPermalink()
    {
        return permalink;
    }
    public void setPermalink(String permalink)
    {
        this.permalink = permalink;
    }
    public String getFullName()
    {
        return fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

}
