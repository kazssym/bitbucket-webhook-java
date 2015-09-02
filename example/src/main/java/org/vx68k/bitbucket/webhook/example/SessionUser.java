/*
 * SessionUser
 * Copyright (C) 2015 Nishimura Software Studio
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket.webhook.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Service;
import org.vx68k.bitbucket.api.client.oauth.OAuthRedirection;

/**
 * User of this web application.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default port for the http scheme.
     */
    private static final int DEFAULT_HTTP_PORT = 80;

    /**
     * Default port for the https scheme.
     */
    private static final int DEFAULT_HTTPS_PORT = 443;

    private ApplicationConfig applicationConfig;

    private transient Service bitbucketService;

    public SessionUser() {
    }

    public SessionUser(ApplicationConfig applicationConfig) {
        setApplicationConfig(applicationConfig);
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public boolean isAuthenticated() {
        if (bitbucketService == null) {
            return false;
        }
        return bitbucketService.isAuthenticated();
    }

    @Inject
    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public String login() throws URISyntaxException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request
                = (HttpServletRequest) externalContext.getRequest();

        // Redirects the user agent to the authorization endpoint.
        Client bitbucketClient = applicationConfig.getBitbucketClient();
        StringBuilder path = new StringBuilder(request.getContextPath());
        URI redirectionEndpoint = new URI(
                request.getScheme(), null, request.getServerName(),
                getExplicitServerPort(request), getRedirectionPath(request),
                null, null);
        bitbucketClient.setRedirectionEndpoint(redirectionEndpoint);

        HttpSession session = request.getSession();
        URI authorizationEndpoint
                = bitbucketClient.getAuthorizationEndpoint(session.getId());
        externalContext.redirect(authorizationEndpoint.toString());

        return null;
    }

    public void requestToken(@Observes OAuthRedirection redirection)
            throws IOException {
        HttpServletRequest request = redirection.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String state = request.getParameter("state");
            if (state != null && state.equals(session.getId())) {
                // The redirection is for this session.

                String code = request.getParameter("code");
                if (code != null) {
                    // The resource access was authorized.
                    Client bitbucketClient
                            = applicationConfig.getBitbucketClient();
                    bitbucketService = bitbucketClient.getService(code);

                    HttpServletResponse response = redirection.getResponse();
                    StringBuilder path = new StringBuilder(
                            request.getContextPath());
                    if (request.getPathInfo() != null) {
                        path.append(request.getPathInfo());
                    }
                    response.sendRedirect(path.toString());
                }
            }
        }
    }

    /**
     * Returns the server port of a HTTP request.
     * @param request HTTP request
     * @return server port, or -1 if it is the default of the scheme
     */
    protected static int getExplicitServerPort(HttpServletRequest request) {
        int port = request.getServerPort();
        if (port == DEFAULT_HTTP_PORT
                && "http".equals(request.getScheme())) {
            return -1;
        }
        if (port == DEFAULT_HTTPS_PORT
                && "https".equals(request.getScheme())) {
            port = -1;
        }
        return port;
    }

    /**
     * Returns the redirection endpoint path.
     * @param request HTTP request
     * @return redirection endpoint path
     */
    protected static String getRedirectionPath(HttpServletRequest request) {
        StringBuilder path = new StringBuilder(request.getContextPath());
        path.append("/authorized");
        if (request.getServletPath() != null) {
            path.append(request.getServletPath());
        }
        if (request.getPathInfo() != null) {
            path.append(request.getPathInfo());
        }
        return path.toString();
    }
}
