/*
 * ApplicationConfig
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

import java.io.Serializable;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import org.vx68k.bitbucket.api.client.Client;
import org.vx68k.bitbucket.api.client.Credentials;
import org.vx68k.bitbucket.api.client.User;

/**
 * Application configuration.
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("config")
public class ApplicationConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String BITBUCKET_OAUTH_CLIENT_ID_ENV =
            "BITBUCKET_OAUTH_CLIENT_ID";

    private static final String BITBUCKET_OAUTH_CLIENT_SECRET_ENV =
            "BITBUCKET_OAUTH_CLIENT_SECRET";

    @PersistenceContext(unitName = "org.vx68k.bitbucket.webhook.example")
    private EntityManager entityManager;

    /**
     * Tests whether a Bitbucket user is an administrator or not.
     * @param user Bitbucket user
     * @return <code>true</code> if the Bitbucket user is an administrator, or
     * <code>false</code> otherwise
     * @since 2.0
     */
    public boolean isAdministrator(User user) {
        // TODO: Check if the current user is an administrator.
        return true;
    }

    @Produces
    public static Client getBitbucketClient() {
        String clientId = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_ID,
                System.getenv(BITBUCKET_OAUTH_CLIENT_ID_ENV));
        String clientSecret = System.getProperty(
                Properties.BITBUCKET_OAUTH_CLIENT_SECRET,
                System.getenv(BITBUCKET_OAUTH_CLIENT_SECRET_ENV));

        Client client = new Client();
        if (clientId != null && clientSecret != null) {
            client.setCredentials(new Credentials(clientId, clientSecret));
        }
        return client;
    }

    /**
     * Administrator entity.
     * @since 2.0
     */
    @Entity(name = "administrator")
    public static class Administrator {

        @Id
        @Column(name = "uuid")
        private UUID uuid;

        /**
         * Returns the user UUID of this object.
         * @return user UUID
         */
        public UUID getUuid() {
            return uuid;
        }

        /**
         * Sets the user UUID of this object.
         * @param uuid user UUID
         */
        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
