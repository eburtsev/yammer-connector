/**
 * This file was automatically generated by the Mule Cloud Connector Development Kit
 */

package org.mule.yammer;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.param.Optional;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.HMAC_SHA1;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Connector for Yammer related functions.
 * 
 * @author MuleSoft, Inc. 
 */
@Module(name = "yammer", schemaVersion="2.0")
public class YammerConnector implements Initialisable {

    protected transient Log logger = LogFactory.getLog(getClass());

    @Configurable
    private String consumerKey;

    @Configurable
    private String consumerSecret;

    @Configurable
    @Optional
    private boolean debug;

    protected String oauthVerifier;

    // @Configurable - will reenable when CC supports this.
    private Client client;

    private String oauthTokenSecret;

    private String oauthToken;

    @Configurable
    @Optional
    private String accessToken;

    @Configurable
    @Optional
    private String accessTokenSecret;

    @Override
    public void initialise() throws InitialisationException
    {
        if (client == null)
        {
            DefaultClientConfig config = new DefaultClientConfig();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JacksonJsonProvider provider = new JacksonJsonProvider(Annotations.JACKSON);
            provider.setMapper(mapper);
            config.getSingletons().add(provider);
            client = Client.create(config);
        }

        if (debug)
        {
            client.addFilter(new LoggingFilter());
        }
    }

    /**
     * Set the OAuth verifier after it has been retrieved via requestAuthorization. The resulting access tokens
     * will be logged to the INFO level so the user can reuse them as part of the configuration in the future
     * if desired.
     * 
     * @param oauthVerifier The OAuth verifier code from Yammer.
     */
    @Processor
    public void setOauthVerifier(String oauthVerifier)
    {
        this.oauthVerifier = oauthVerifier;

        WebResource resource = client.resource("https://www.yammer.com/oauth/access_token");
        // Set the OAuth parameters
        OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret)
            .tokenSecret(oauthTokenSecret);
        OAuthParameters params = new OAuthParameters().consumerKey(consumerKey)
            .verifier(oauthVerifier)
            .signatureMethod("PLAINTEXT")
            .version("1.0");

        params.put("oauth_token", oauthToken);

        // Create the OAuth client filter
        OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);
        // Add the filter to the resource
        resource.addFilter(filter);

        ClientResponse post = resource.post(ClientResponse.class);

        handleErrors(post);

        Form form = post.getEntity(Form.class);
        accessToken = form.getFirst("oauth_token");
        accessTokenSecret = form.getFirst("oauth_token_secret");

        logger.info("Got OAuth access tokens. Access token:" + accessToken + " Access token secret:"
                    + accessTokenSecret);
    }

    /**
     * Start the OAuth request authorization process. This will request a token from
     * Yammer and return a URL which the user can visit to authorize the connector
     * for their account.
     * 
     * @return The user authorization URL.
     */
    @Processor
    public String requestAuthorization()
    {
        WebResource resource = client.resource("https://www.yammer.com/oauth/request_token");
        // Set the OAuth parameters
        OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret);
        OAuthParameters params = new OAuthParameters().consumerKey(consumerKey)
            .signatureMethod("HMAC-SHA1")
            .version("1.0");
        // Create the OAuth client filter
        OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);
        // Add the filter to the resource
        resource.addFilter(filter);

        ClientResponse post = resource.post(ClientResponse.class);

        handleErrors(post);

        Form form = post.getEntity(Form.class);
        oauthToken = form.getFirst("oauth_token");
        oauthTokenSecret = form.getFirst("oauth_token_secret");

        return "https://www.yammer.com/oauth/authorize?oauth_token=" + oauthToken;
    }

    private void handleErrors(ClientResponse response)
    {
        if (response.getStatus() >= 300)
        {
            String entity = response.getEntity(String.class);
            throw new RuntimeException("Got status: " + response.getStatus() + ".\nMessage: " + entity);
        }
    }

    @Processor
    public List<Message> getMessages()
    {
        return getMessages("https://www.yammer.com/api/v1/messages.json");
    }

    @Processor
    public List<Message> getSentMessages()
    {
        return getMessages("https://www.yammer.com/api/v1/messages/sent.json");
    }

    @Processor
    public List<Message> getReceivedMessages()
    {
        return getMessages("https://www.yammer.com/api/v1/messages/received.json");
    }

    @Processor
    public List<Message> getPrivateMessages()
    {
        return getMessages("https://www.yammer.com/api/v1/messages/private.json");
    }

    @Processor
    public List<Message> getFollowingMessages()
    {
        return getMessages("https://www.yammer.com/api/v1/messages/following.json");
    }

    private List<Message> getMessages(String url)
    {
        ClientResponse response = oauthResource(url).get(ClientResponse.class);
        List<Message> messages = response.getEntity(Messages.class).getMessages();

        if (messages == null)
        {
            return Collections.emptyList();
        }
        return messages;
    }

    /**
     * Creates a WebResource with the proper oauth authentication information.
     * 
     * @param url
     * @return
     */
    protected WebResource oauthResource(String url)
    {
        WebResource resource = client.resource(url);
        OAuthParameters params = new OAuthParameters().signatureMethod(HMAC_SHA1.NAME).consumerKey(
            consumerKey).token(accessToken).version();

        OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret).tokenSecret(
            accessTokenSecret);

        resource.addFilter(new OAuthClientFilter(client.getProviders(), params, secrets));
        return resource;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public String getConsumerKey()
    {
        return consumerKey;
    }

    public void setConsumerKey(String applicationKey)
    {
        this.consumerKey = applicationKey;
    }

    public String getConsumerSecret()
    {
        return consumerSecret;
    }

    public void setConsumerSecret(String applicationSecret)
    {
        this.consumerSecret = applicationSecret;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public String getOauthTokenSecret()
    {
        return oauthTokenSecret;
    }

    public void setOauthTokenSecret(String oauthTokenSecret)
    {
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public String getOauthToken()
    {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken)
    {
        this.oauthToken = oauthToken;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String token)
    {
        this.accessToken = token;
    }

    public String getAccessTokenSecret()
    {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String secret)
    {
        this.accessTokenSecret = secret;
    }

}
