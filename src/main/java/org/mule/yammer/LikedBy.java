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

public class LikedBy
{
    private int count;
    private List<Name> names;

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public List<Name> getNames()
    {
        return names;
    }

    public void setNames(List<Name> names)
    {
        this.names = names;
    }

}
