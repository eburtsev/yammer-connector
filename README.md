Mule Yammer Connector
=====================

The Yammer Connector currently makes it possible to query different timelines 
available to a user, such as the public, friends or private timeline.

Installation and Usage
----------------------

For information about usage and installation you can check our documentation at http://mulesoft.github.com/yammer-connector.

It's necessary to set in yammer (https://www.yammer.com/client_applications where you can create an application or edit it) the next Callback URL:

    http://{host}:{port}/yammerCodeRetrievingPath

    where the {host} is the same host as the Website of the yammer application.

Then you have to configure yammer:

    <http:connector name="connector.http.mule.default" enableCookies="true" keepAlive="true" />
    
    <yammer:config consumerKey="${consumerKey}" consumerSecret="${consumerSecret}">
        <yammer:http-callback-config domain="{host}" remotePort="SOME_PORT" localPort="{port}" />
    </yammer:config>
    
    where {host} and {port} are the same of the Callback URL used before.

Reporting Issues
----------------

We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link https://github.com/mulesoft/yammer-connector/issues.







