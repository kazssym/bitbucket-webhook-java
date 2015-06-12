/*
 * BitbucketUser - represents a Bitbucket user
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

package org.vx68k.bitbucket.webhook;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.json.JsonObject;

/**
 * Represents a Bitbucket user.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketUser {

    private String username;

    private String displayName;

    private UUID uuid;

    private final Map<String, URL> links = new HashMap<String, URL>();

    /**
     * Returns the username of this user.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the display name of this user.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the UUID of this user.
     *
     * @return UUID
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the links of this user.
     *
     * @return map of the links
     */
    public Map<String, URL> getLinks() {
        return links;
    }

    /**
     * Sets the username of this user.
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the display name of this user.
     *
     * @param displayName new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the UUID of this user.
     *
     * @param uuid new UUID
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Sets the links of this user.
     * @param links map of the new links
     */
    public void setLinks(Map<String, URL> links) {
        this.links.clear();
        this.links.putAll(links);
    }

    /**
     * Creates a {@link BitbucketUser} object from a JSON object.
     *
     * @param object JSON object which represents a Bitbucket user
     * @return created {@link BitbucketUser} object
     * @exception NullPointerException if any required fields are missing
     */
    public static BitbucketUser fromJsonObject(JsonObject object) {
        BitbucketUser user = new BitbucketUser();
        user.setUsername(object.getString("username"));
        user.setDisplayName(object.getString("display_name"));
        user.setUuid(BitbucketUtilities.parseUuid(object.getString("uuid")));
        // TODO: Parse links.
        return user;
    }
}
