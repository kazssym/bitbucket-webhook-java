/*
 * WebAppUser
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
import java.net.UnknownServiceException;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import org.vx68k.bitbucket.api.client.BitbucketClient;

/**
 * User of this web application.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class WebAppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private WebAppConfig config;

    private String accessToken;

    private Date expiration;

    private String refreshToken;

    public WebAppUser() {
    }

    public WebAppUser(WebAppConfig config) {
        this.config = config;
    }

    public WebAppConfig getConfig() {
        return config;
    }

    public boolean isAuthenticated() {
        if (accessToken == null) {
            return false;
        }
        return expiration == null || !expiration.before(new Date());
    }

    @Inject
    public void setConfig(WebAppConfig config) {
        this.config = config;
    }

    public String login() throws IOException {
        BitbucketClient bitbucketClient = config.getBitbucketClient();
        AuthorizationCodeFlow flow
                = bitbucketClient.getAuthorizationCodeFlow(false);
        if (flow == null) {
            throw new IllegalStateException("No client credentials");
        }

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        AuthorizationRequestUrl requestUrl = flow.newAuthorizationUrl();
        requestUrl.setState(session.getId());
        externalContext.redirect(requestUrl.build());

        return null;
    }

    public void requestToken(String authorizationCode) throws IOException {
        BitbucketClient bitbucketClient = config.getBitbucketClient();
        AuthorizationCodeFlow flow
                = bitbucketClient.getAuthorizationCodeFlow(true);
        if (flow == null) {
            throw new IllegalStateException("No client credentials");
        }

        AuthorizationCodeTokenRequest tokenRequest
                = flow.newTokenRequest(authorizationCode);
        TokenResponse tokenResponse = tokenRequest.execute();
        if (!tokenResponse.getTokenType().equals("bearer")) {
            throw new UnknownServiceException("Unsupported token type");
        }
        accessToken = tokenResponse.getAccessToken();
        Long expiresIn = tokenResponse.getExpiresInSeconds();
        expiration = null;
        if (expiresIn != null) {
            expiration = new Date();
            expiration.setTime(
                    expiration.getTime() + expiresIn.longValue() * 1000);
        }
        refreshToken = tokenResponse.getRefreshToken();
    }
}
