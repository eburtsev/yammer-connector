Mule Yammer Connector
=====================

The Yammer Connector currently makes it possible to query different timelines 
available to a user, such as the public, friends or private timeline. For example:

    <?xml version="1.0" encoding="UTF-8"?>
    <mule xmlns="http://www.mulesoft.org/schema/mule/core" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:spring="http://www.springframework.org/schema/beans"
        xmlns:yammer="http://www.mulesoft.org/schema/mule/yammer"
        xmlns:json="http://www.mulesoft.org/schema/mule/json"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                              http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/3.1/mule.xsd
                              http://www.mulesoft.org/schema/mule/json http://www.mulesoft.org/schema/mule/json/3.1/mule-json.xsd
                              http://www.mulesoft.org/schema/mule/yammer http://www.mulesoft.org/schema/mule/yammer/1.0-SNAPSHOT/mule-yammer.xsd">
                              
        <yammer:config consumerKey="${consumer.key}" consumerSecret="${consumer.secret}"/>
        
        <flow name="YammerTest">
            <inbound-endpoint address="http://localhost:9002/get-statuses"/>
            <response>
                <json:object-to-json-transformer/>
            </response>
            <yammer:get-messages/>
        </flow>
        ....
        
Authenticating with OAuth
-------------------------
With OAuth 1.0, you must do a two step process to authenticate the connector. 
First, request authorization from Yammer. Then, submit the OAuth verifier code back 
to the connector.

You'll need to set up the following two flows to do this:

    <flow name="request-authorization">
        <inbound-endpoint address="http://localhost:9002/yammer/request-authorization"/>
        <yammer:request-authorization/>
        <message-properties-transformer>
            <add-message-property key="http.status" value="302"/>
            <add-message-property key="Location" value="#[payload]"/>
        </message-properties-transformer>
    </flow>
    
    <flow name="set-oauth-verifier">
        <inbound-endpoint address="http://localhost:9002/yammer/set-oauth-verifier"/>
        <logger />
        <yammer:set-oauth-verifier oauthVerifier="#[header:inbound:verifier]"/>
    </flow>
    
The first flow will redirect you to the Yammer authorization page once the connector has
requested an authentication token. The second flow will allow you to set the OAuth verifier code.

To authenticate your connector, do the following:

- [Create a Yammer account](http://yammer.com)
- [Register your application](https://www.yammer.com/client_applications/new) with Yammer  
- Set your OAuth consumer key and secret on your config element as shown above
- Go to http://localhost:9002/yammer/request-authorization in your browser
- You will be redirected to the Yammer authorization page. Click Authorize.
- Take the resulting OAuth verifier code and go to http://localhost:9002/yammer/set-oauth-verifier?verifier=OAUTH_VERIFIER in your browser.
- Your access token and secret will be logged to your application logs. You can use those to avoid future authentication setup by setting the accessToken and accessTokenSecret attributes on the connector.

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>muleforge.webdav.releases</id>
            <name>MuleSoft Release Repository</name>
            <url>https://repository.muleforge.org/release/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-yammer</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <yammer:config consumerKey="value" consumerSecret="value" debug="value" accessToken="value" accessTokenSecret="value"/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
|consumerKey||no|
|consumerSecret||no|
|debug||yes|
|accessToken||yes|
|accessTokenSecret||yes|




Set Oauth Verifier
------------------

Set the OAuth verifier after it has been retrieved via requestAuthorization. The resulting access tokens
will be logged to the INFO level so the user can reuse them as part of the configuration in the future
if desired.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|oauthVerifier|The OAuth verifier code from Yammer.|no||



Request Authorization
---------------------

Start the OAuth request authorization process. This will request a token from Yammer and return
a URL which the user can visit to authorize the connector for their account.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns user authorization URL.



Get Messages
------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Get Sent Messages
-----------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Get Received Messages
---------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Get Private Messages
--------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||



Get Following Messages
----------------------

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||





























































