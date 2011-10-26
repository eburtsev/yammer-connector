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

import org.mule.tck.junit4.FunctionalTestCase;

import org.junit.Test;


public class YammerSchemaTest extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "yammer-schema-test.xml";
    }

    /**
     * Tests that the schema can be parsed 
     */
    @Test
    public void canParse() throws Exception {
        // nothing. will fail if schema can not parse 
    }
}
