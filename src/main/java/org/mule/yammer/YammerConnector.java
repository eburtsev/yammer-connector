/**
 * Mule Yammer Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
/**
 * This file was automatically generated by the Mule Cloud Connector Development
 * Kit
 */
package org.mule.yammer;

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
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.oauth.OAuth2;
import org.mule.api.annotations.oauth.OAuthAccessToken;
import org.mule.api.annotations.oauth.OAuthConsumerKey;
import org.mule.api.annotations.oauth.OAuthConsumerSecret;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.springframework.http.HttpStatus;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.mule.api.annotations.param.Payload;

/**
 * Connector for Yammer related functions.
 *
 * <a href="https://www.yammer.com">Yammer</a> is a social-network-like internal corporate communications system,
 * where users are able to post messages, follow users,
 * images and so on in a manner like Facebook or Twitter.
 *
 * This connector allows to:
 * <ul>
 *  <li>Retrieve messages</li>
 *  <li>Retrieve received messages</li>
 *  <li>Retrieve private messages</li>
 *  <li>Retrieve sent messages</li>
 * </ul>
 * <p>
 * It's necessary to set in yammer (https://www.yammer.com/client_applications where you can create an application or edit it) the next Callback URL:<br/>
 *<br/>
 *    http://{host}:{port}/yammerCodeRetrievingPath<br/>
 *<br/>
 *    where the {host} is the same host as the Website of the yammer application.<br/>
 *<br/>
 * Then you have to configure yammer:<br/>
 *<br/>
 *    &lt;http:connector name="connector.http.mule.default" enableCookies="true" keepAlive="true" /&gt;<br/>
 *<br/>
 *    &lt;yammer:config consumerKey="${consumerKey}" consumerSecret="${consumerSecret}"&gt;<br/>
 *        &lt;yammer:http-callback-config domain="{host}" remotePort="SOME_PORT" localPort="{port}" /&gt;<br/>
 *    &lt;/yammer:config&gt;<br/>
 *<br/>
 *    where {host} and {port} are the same of the Callback URL used before.<br/>
 *</p>
 *
 * @author MuleSoft, Inc.
 */
@Module(name = "yammer", schemaVersion="2.0")
@OAuth2(authorizationUrl = "https://www.yammer.com/dialog/oauth",
        accessTokenUrl = "https://www.yammer.com/oauth2/access_token.json",
        callbackPath = "yammerCodeRetrievingPath",
        accessTokenRegex = "\"token\":\"([^&]+?)\"")
public class YammerConnector {

    protected transient Log logger = LogFactory.getLog(getClass());
    /**
     * The OAuth consumer key
     */
    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    /**
     * The OAuth consumer secret
     */
    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;

    /**
     * If connector should be run in debug mode. This enables logging of HTTP
     * activity against Yammer
     */
    @Configurable
    @Optional
    @Default("false")
    private boolean debug;
    // @Configurable - will reenable when CC supports this.
    private Client client;

    @PostConstruct
    public void initialise() {
        if (client == null) {
            DefaultClientConfig config = new DefaultClientConfig();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JacksonJsonProvider provider = new JacksonJsonProvider(Annotations.JACKSON);
            provider.setMapper(mapper);
            config.getSingletons().add(provider);
            client = Client.create(config);
        }

        if (debug) {
            client.addFilter(new LoggingFilter());
        }
    }

    /**
     * Logins specified user
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param url URL returned by authorize call in Location header
     * @param username Username
     * @param password Password
     * @return the list of {@link Message}s
     */
    @Processor
    public void login(String url, String username, String password) throws IOException {
        logger.info(String.format("login(%s, %s, %s)", url, username, password));
        HttpConnection httpConnection = new HttpConnection();
        // Request login page
        String content = httpConnection.requestToUrl(new HttpGet(url));

        String authenticityToken = extractStringWithRegex(content, ".*?<input name=\"authenticity_token\" type=\"hidden\" value=\"(.*?)\" />.*?", 1);
        String utf8 = extractStringWithRegex(content, ".*?<input name=\"utf8\" type=\"hidden\" value=\"(.*?)\" />.*?", 1);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("authenticity_token", authenticityToken));
        formParams.add(new BasicNameValuePair("login", username));
        formParams.add(new BasicNameValuePair("password", password));
        formParams.add(new BasicNameValuePair("remember_me", "on"));
        formParams.add(new BasicNameValuePair("network_permalink", ""));
        if (null != utf8) {
            formParams.add(new BasicNameValuePair("utf8", utf8));
        }
        HttpEntity entity = new UrlEncodedFormEntity(formParams, "utf-8");
        HttpPost httpPost = new HttpPost("https://www.yammer.com/session");
        httpPost.setEntity(entity);
        // Perform login
        content = httpConnection.requestToUrl(httpPost);
        // Extract link which allow access for application
        String allowLink = extractStringWithRegex(content, "<div class=\"allow-deny_container\">.*?<a href=\"(https://www.yammer.com/.*?/oauth2/.*?/authorize.*?)\".*?><div.*?>Allow</div></a>.*", 1);
        // Allow access
        httpConnection.requestToUrl(new HttpGet(allowLink));
        httpConnection.close();
    }

    private String extractStringWithRegex(String content, String regex, int group) {
        String contentPrepared = content.replaceAll("[\n\r]", "");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contentPrepared);
        if (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

    private static final SimpleDateFormat DATE_FORMAT_IN = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");

    /**
     * Answers all messages in this network. Corresponds to the "Company Feed"
     * tab on the website.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param duration duration
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getMessages(@OAuthAccessToken String accessToken, @Optional Integer duration) {
        List<Message> allMessages = getMessages("https://www.yammer.com/api/v1/messages.json", accessToken);
        return getMessages(allMessages, duration);
    }

    /**
     * Answers all messages in threads. Corresponds to the "Company Feed"
     * tab on the website.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param ids ids
     * @param duration duration
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getThreadMessages(@OAuthAccessToken String accessToken, @Payload List<String> ids, @Optional Integer duration) {
        List<Message> messages = new ArrayList<Message>();
        for (String id : ids) {
            List<Message> threadMessages = getMessages(String.format("https://www.yammer.com/api/v1/messages/in_thread/%s.json", id), accessToken);
            messages.addAll(getMessages(threadMessages, duration));
        }
        return messages;
    }

    private List<Message> getMessages(List<Message> allMessages, Integer duration) {
        try {
            if (null != duration) {
                List<Message> messages = new ArrayList<Message>();
                for (Message message : allMessages) {
                    Date msgDate = DATE_FORMAT_IN.parse(message.getCreatedAt());
                    if (msgDate.compareTo(DateUtils.addMinutes(new Date(), -duration)) >= 0) {
                        messages.add(message);
                    }
                }
                return messages;
            }
        } catch (java.text.ParseException ex) {
            throw new RuntimeException(ex);
        }
        return allMessages;
    }

    /**
     * Answers the whole list of messages sent by the current user. Corresponds
     * to the "Sent" tab on the website.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-sent-messages}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getSentMessages(@OAuthAccessToken String accessToken) {
        return getMessages("https://www.yammer.com/api/v1/messages/sent.json", accessToken);
    }

    /**
     * Answers the list of messages received by the logged-in user. Corresponds
     * to the "Received" tab on the website.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-received-messages}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getReceivedMessages(@OAuthAccessToken String accessToken) {
        return getMessages("https://www.yammer.com/api/v1/messages/received.json", accessToken);
    }

    /**
     * Answers the whole list of private Messages (aka Direct Messages) for the
     * logged-in user. Corresponds to the "Direct Messages" section on the
     * website.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-private-messages}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getPrivateMessages(@OAuthAccessToken String accessToken) {
        return getMessages("https://www.yammer.com/api/v1/messages/private.json", accessToken);
    }

    /**
     * Answers the list of messages followed by the logged-in user. Corresponds
     * to the "My Feed" tab on the website. 
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-following-messages}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link Message}s
     */
    @Processor
    public List<Message> getFollowingMessages(@OAuthAccessToken String accessToken) {
        return getMessages("https://www.yammer.com/api/v1/messages/following.json", accessToken);
    }

    /**
     * Answers the list of groups
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:list-users}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link Group}s
     */
    @Processor
    public List<Group> listGroups(@OAuthAccessToken String accessToken) {
        return getGroups(accessToken);
    }

    /**
     * Creates group
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param name name
     * @param description description
     * @param isPrivate isPrivate
     * 
     */
    @Processor
    public void createGroup(@OAuthAccessToken String accessToken, String name, @Optional String description, @Optional Boolean isPrivate) {
        createGroupInternal(accessToken, name, description, isPrivate);
    }

    /**
     * Get group by name
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param name name
     * 
     * @return {@link Group} object
     */
    @Processor
    public Group getGroup(String accessToken, String name) {
        if (!StringUtils.isEmpty(name)) {
            List<Group> groups = listGroups(accessToken);
            for (Group group : groups) {
                if (name.replaceAll("\\s", "").equalsIgnoreCase(group.getName())) {
                    return group;
                }
            }
        }
        return null;
    }

    /**
     * Answers the list of users 
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:list-users}
     *
     * @param accessToken OAuth access token
     * @return the list of {@link User}s
     */
    @Processor
    public List<User> listUsers(@OAuthAccessToken String accessToken) {
        return getUsers("https://www.yammer.com/api/v1/users.json", accessToken);
    }

    /**
     * Creates user
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param email email
     * @param imProvider imProvider
     * @param imUsername imUsername
     * @param fullName fullName
     * @param jobTitle jobTitle
     * @param significantOther significantOther
     * @param kidsNames kidsNames
     * @param interests interests
     * @param summary summary
     * @param expertise expertise
     * 
     */
    @Processor
    public void createUser(@OAuthAccessToken String accessToken, String email,
            @Optional String imProvider, @Optional String imUsername,
            @Optional String fullName, @Optional String jobTitle,
            @Optional String significantOther, @Optional String kidsNames,
            @Optional String interests, @Optional String summary, @Optional String expertise) {
        User user = createUserObject(email, imProvider, imUsername, fullName, jobTitle, significantOther, kidsNames, interests, summary, expertise);
        createUser(accessToken, user);
    }

    /**
     * Updates user
     * <p/>
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-messages}
     *
     * @param accessToken OAuth access token
     * @param email email
     * @param imProvider imProvider
     * @param imUsername imUsername
     * @param fullName fullName
     * @param jobTitle jobTitle
     * @param significantOther significantOther
     * @param kidsNames kidsNames
     * @param interests interests
     * @param summary summary
     * @param expertise expertise
     * 
     */
    @Processor
    public void updateUser(@OAuthAccessToken String accessToken, String email,
            @Optional String imProvider, @Optional String imUsername,
            @Optional String fullName, @Optional String jobTitle,
            @Optional String significantOther, @Optional String kidsNames,
            @Optional String interests, @Optional String summary, @Optional String expertise) {
        User user = createUserObject(email, imProvider, imUsername, fullName, jobTitle, significantOther, kidsNames, interests, summary, expertise);
        updateUser(accessToken, email, user);
    }

    /**
     * Get user by email
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-user}
     *
     * @param accessToken OAuth access token
     * @param email User email
     * @return the list of {@link Message}s
     */
    @Processor
    public User getUser(@OAuthAccessToken String accessToken, String email) {
        return getUserFromUrl(String.format("https://www.yammer.com/api/v1/users/by_email.json?email=%s", email), accessToken);
    }

    /**
     * Join to group on behalf of user
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-user}
     *
     * @param accessToken OAuth access token
     * @param email User email
     * @param groupName groupName
     * @return the list of {@link Message}s
     */
    @Processor
    public String joinGroup(@OAuthAccessToken String accessToken, String email, String groupName) {
        Group group = getGroup(accessToken, groupName);
        if (null == group)
            return "NO_SUCH_GROUP";
        String userToken = getToken(accessToken, email).getToken();
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/group_memberships.json?group_id=" + group.getId(), userToken);
        return resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class);
    }

    /**
     * Join to group on behalf of user
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:get-user}
     *
     * @param accessToken OAuth access token
     * @param email User email
     * @param groupName groupName
     * @return the list of {@link Message}s
     */
    @Processor
    public String leaveGroup(@OAuthAccessToken String accessToken, String email, String groupName) {
        Group group = getGroup(accessToken, groupName);
        if (null == group)
            return "NO_SUCH_GROUP";
        String userToken = getToken(accessToken, email).getToken();
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/group_memberships.json?group_id=" + group.getId(), userToken);
        return resource.type(MediaType.APPLICATION_FORM_URLENCODED).delete(String.class);
    }

    /**
     * Creates a new message in the account of the logged-in user.
     * {@sample.xml ../../../doc/mule-module-yammer.xml.sample yammer:create-message}
     *
     * @param accessToken OAuth access token
     * @param body the content of the message to create
     * @param groupName groupName
     * @param userEmail userEmail
     * @return the message created.
     */
    @Processor
    public Messages createMessage(@OAuthAccessToken String accessToken, String body, @Optional String groupName, @Optional String userEmail) {
        System.out.println("AccessTOKEN = " + accessToken);
        Form form = new Form();
        form.add("body", body);
        Group group = getGroup(accessToken, groupName);
        if (null != group) {
            form.add("group_id", group.getId());
        } else {
            return new Messages();
        }
        if (null != userEmail) {
            String token = getToken(accessToken, userEmail).getToken();
            accessToken = StringUtils.isNotEmpty(token) ? token : accessToken;
        }
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/messages.json", accessToken);
        return resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(Messages.class, form);
    }

    private Token getToken(String accessToken, String userEmail) {
        Form form = new Form();
        List<User> users = getUsers("https://www.yammer.com/api/v1/users.json", accessToken);
        for (User u : users) {
            UserContacts contacts = u.getContact();
            if (null != contacts
                    && null != contacts.getEmailAddresses()
                    && contacts.getEmailAddresses().length > 0
                    && userEmail.equals(contacts.getEmailAddresses()[0].getAddress())) {
                System.out.println(u.toString());
                form.add("user_id", u.getId());
            }
        }
        form.add("consumer_key", consumerKey);
        //WebResource resource = oauthResource("https://www.yammer.com/api/v1/oauth/tokens.json?user_id=" + form.getFirst("user_id"), accessToken);
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/oauth.json", accessToken);
        Token ss = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(Token.class, form);
        System.out.println("!!! token: " + ss);
        return ss;
    }

    private List<Message> getMessages(String url, String accessToken) {
        List<Message> messages = getGenericObject(url, accessToken, Messages.class).getMessages();

        if (messages == null) {
            return Collections.emptyList();
        }
        return messages;
    }

    private Form convertUserObjectToHttpForm(User user) {
        Form form = new Form();
        form.add("email", user.getContact().getEmailAddresses()[0].getAddress());
        form.add("full_name", user.getFullName());
        form.add("job_title", user.getJobTitle());
        form.add("im_provider", user.getContact().getImAddress().getProvider());
        form.add("im_username", user.getContact().getImAddress().getUsername());
        form.add("significant_other", user.getSignificantOther());
        form.add("kids_names", user.getKidsNames());
        form.add("interests", user.getInterests());
        form.add("summary", user.getSummary());
        form.add("expertise", user.getExpertise());
        return form;
    }

    private User createUserObject(String email, String im_provider, String im_username,
            String full_name, String job_title, String significant_other, String kids_names, String interests, String summary, String expertise) {
        User user = new User();
        UserContacts contacts = new UserContacts();
        contacts.setEmailAddresses(new EmailAddress[] { new EmailAddress("home", email) });
        if (null != im_provider && null != im_username) {
            contacts.setImAddress(new ImAddress(im_provider, im_username));
        }
        user.setContact(contacts);
        user.setFullName(full_name);
        user.setJobTitle(job_title);
        user.setSignificantOther(significant_other);
        user.setKidsNames(kids_names);
        if (null != interests) {
            user.setInterests(interests);
        }
        user.setSummary(summary);
        user.setExpertise(expertise);
        return user;
    }

    private User createUser(String accessToken, User user) {
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/users.json", accessToken);
        Form form = convertUserObjectToHttpForm(user);
        ClientResponse response = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
        System.out.println("============================================");
        System.out.println(response.getStatus());
        if (response.getStatus() != HttpStatus.OK.value()) {
            throw new RuntimeException(response.getEntity(String.class));
        }

        System.out.println("============================================");

        return user;
    }

    private User updateUser(String accessToken, String email, User user) {
        List<User> users = getUsers("https://www.yammer.com/api/v1/users.json", accessToken);
        for (User u : users) {
            UserContacts contacts = u.getContact();
            if (null != contacts
                    && null != contacts.getEmailAddresses()
                    && contacts.getEmailAddresses().length > 0
                    && email.equals(contacts.getEmailAddresses()[0].getAddress())) {
                String url = String.format("https://www.yammer.com/api/v1/users/%s.json", u.getId());
                System.out.println(url);
                WebResource resource = oauthResource(url, accessToken);
                Form form = convertUserObjectToHttpForm(user);
                ClientResponse response = resource.type(MediaType.APPLICATION_FORM_URLENCODED).put(ClientResponse.class, form);
                System.out.println("============================================");
                System.out.println(response.getStatus());
                for (String key : response.getHeaders().keySet()) {
                    System.out.println(String.format("%s: %s", key, response.getHeaders().get(key)));
                }
                if (response.getStatus() != HttpStatus.OK.value()) {
                    throw new RuntimeException(response.getEntity(String.class));
                }
                System.out.println("============================================");
                return user;
            }
        }
        return null;
    }

    private User getUserFromUrl(String url, String accessToken) {
        return getGenericObject(url, accessToken, User.class);
    }

    private <T> T getGenericObject(String url, String accessToken, Class<T> clazz) {
        ClientResponse response = oauthResource(url, accessToken).get(ClientResponse.class);
        if (response.getStatus() != HttpStatus.OK.value()) {
            throw new RuntimeException(response.getEntity(String.class));
        }

//        System.out.println(response.getEntity(String.class));

        T object = response.getEntity(clazz);

        return object;
    }

    private List<User> getUsers(String url, String accessToken) {
        Users users = getGenericObject(url, accessToken, Users.class);

        if (users == null) {
            return Collections.emptyList();
        }
        return users;
    }

    private List<Group> getGroups(String accessToken) {
        Groups groups = getGenericObject("https://www.yammer.com/api/v1/groups.json", accessToken, Groups.class);

        if (groups == null) {
            return Collections.emptyList();
        }
        return groups;
    }

    private void createGroupInternal(String accessToken, String name, String description, Boolean isPrivate) {
        WebResource resource = oauthResource("https://www.yammer.com/api/v1/groups", accessToken);
        Form form = new Form();
        form.add("name", name);
        form.add("description", description);
        if (isPrivate) {
            form.add("private", "true");
        }
        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);
    }

    /**
     * Creates a WebResource with the proper oauth authentication information.
     *
     * @param accessToken OAuth access token
     * @param url the url of the resource
     * @return the authenticated {@link WebResource}
     */
    protected WebResource oauthResource(String url, String accessToken) {
        WebResource resource = client.resource(url + (url.contains("?") ? "&" : "?") + "access_token=" + accessToken);
        OAuthParameters params = new OAuthParameters().signatureMethod(HMAC_SHA1.NAME).consumerKey(
                consumerKey).token(accessToken).version("2.0");

        OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret);

        resource.addFilter(new OAuthClientFilter(client.getProviders(), params, secrets));
        return resource;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String applicationKey) {
        this.consumerKey = applicationKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String applicationSecret) {
        this.consumerSecret = applicationSecret;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
