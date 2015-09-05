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
import java.net.URI;
import java.net.URISyntaxException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.vx68k.bitbucket.api.client.User;
import org.vx68k.bitbucket.api.client.oauth.OAuthUser;

/**
 * User of the current session.
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class SessionUser extends OAuthUser {

    private static final long serialVersionUID = 1L;

    private ApplicationConfig applicationConfig;

    /**
     * Returns the application configuration of this object.
     * @return application configuration
     * @since 2.0
     */
    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    /**
     * Sets the application configuration of this object.
     * @param applicationConfig application configuration.
     * @since 2.0
     */
    @Inject
    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    /**
     * Returns the Bitbucket user of this object.
     * @return Bitbucket user, or <code>null</code> if no user is authenticated
     * @throws IOException if an I/O error has occurred
     */
    public User getBitbucketUser() throws IOException {
        return getBitbucketService().getCurrentUser();
    }

    /**
     * Tests whether the current user is an administrator or not.
     * @return <code>true</code> if the current user is an administrator, or
     * <code>false</code> otherwise
     * @throws IOException if an I/O error has occurred
     * @since 2.0
     */
    public boolean isAdministrator() throws IOException {
        User currentUser = getBitbucketUser();
        if (currentUser == null) {
            return false;
        }
        return applicationConfig.isAdministrator(currentUser);
    }

    /**
     * Performs a login action by redirecting the user agent to the
     * authorization endpoint.
     * @return <code>null</code>
     * @throws URISyntaxException if a URI syntax error has been occurred
     * @throws IOException if an I/O error has occurred
     */
    public String login() throws URISyntaxException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request =
                (HttpServletRequest) externalContext.getRequest();

        // Redirects the user agent to the authorization endpoint.
        URI authorizationEndpoint = getAuthorizationEndpoint(request);
        externalContext.redirect(authorizationEndpoint.toString());

        return null;
    }

    /**
     * Performs a logout action by clearing the current Bitbucket service.
     * @return <code>"home"</code>
     */
    public String logout() {
        clearBitbucketService();

        return "home";
    }
}
