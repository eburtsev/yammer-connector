package org.mule.yammer;

import java.io.Closeable;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class HttpConnection implements Closeable {

    protected transient Log logger = LogFactory.getLog(getClass());

    private DefaultHttpClient httpclient;
    private HttpContext httpContext;

    public HttpConnection() {
        httpContext = null;
        httpclient = getThreadSafeClient();
        httpclient.getParams().setParameter(
                CoreProtocolPNames.PROTOCOL_VERSION, new ProtocolVersion("HTTP", 1, 1));
        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
        BasicHttpContext localContext = new BasicHttpContext();

        httpclient.addRequestInterceptor(new PreemptiveAuth(), 0);
        httpContext = localContext;
    }

    public static DefaultHttpClient getThreadSafeClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(
                mgr.getSchemeRegistry()), params);
        return client;
    }

    @Override
    public void close() throws IOException {
        httpclient.getConnectionManager().shutdown();
    }

    private static final class PreemptiveAuth implements HttpRequestInterceptor {

        @Override
        public void process(final HttpRequest request, final HttpContext context)
                throws HttpException, IOException {

            AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

            // If no auth scheme avaialble yet, try to initialize it
            // preemptively
            if (authState.getAuthScheme() == null) {
                AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
                CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (authScheme != null) {
                    Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                    if (creds == null) {
                        throw new HttpException(
                                "No credentials for preemptive authentication");
                    }
                    authState.setAuthScheme(authScheme);
                    authState.setCredentials(creds);
                }
            }
        }
    }

    public String requestToUrl(HttpUriRequest method) {
        String content = "";
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(method, httpContext);
            System.out.println(httpResponse.getStatusLine().toString());
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                for (Header header : httpResponse.getAllHeaders()) {
                    if ("Location".equalsIgnoreCase(header.getName())) {
                        return requestToUrl(new HttpGet(header.getValue()));
                    }
                }
            }
            final HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                content = IOUtils.toString(entity.getContent());
            }
        } catch (IOException e) {
            logger.error(e, e);
        }
        return content;
    }
}
