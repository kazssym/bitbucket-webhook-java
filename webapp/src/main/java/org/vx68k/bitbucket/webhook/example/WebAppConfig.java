/*
 * ApplicationConfig - application configuration
 * Copyright (C) 2015 Kaz Nishimura
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

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Credentials;

/**
 * Application configuration.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("config")
public class WebAppConfig implements Serializable {

    private static final long SerialVersionUID = 1L;

    private static final String BITBUCKET_CLIENT_ID_PROPERTY_NAME
            = "org.vx68k.bitbucket.webhook.example.clientID";
    private static final String BITBUCKET_CLIENT_SECRET_PROPERTY_NAME
            = "org.vx68k.bitbucket.webhook.example.clientSecret";

    private final Client bitbucketClient;

    public WebAppConfig() {
        this(new Client());
    }

    public WebAppConfig(Client bitbucketClient) {
        this.bitbucketClient = bitbucketClient;
        initializeClientCredentials();
    }

    public Client getBitbucketClient() {
        return bitbucketClient;
    }

    private void initializeClientCredentials() {
        Credentials clientCredentials = bitbucketClient.getCredentials();
        clientCredentials.setID(System.getProperty(
                BITBUCKET_CLIENT_ID_PROPERTY_NAME,
                System.getenv("BITBUCKET_CLIENT_ID")));
        clientCredentials.setSecret(System.getProperty(
                BITBUCKET_CLIENT_SECRET_PROPERTY_NAME,
                System.getenv("BITBUCKET_CLIENT_SECRET")));
    }
}
