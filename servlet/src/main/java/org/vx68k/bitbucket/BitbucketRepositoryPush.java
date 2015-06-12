/*
 * BitbucketRepositoryPush - represents a repository push
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

package org.vx68k.bitbucket;

import javax.json.JsonObject;

/**
 * Represents a push to a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketRepositoryPush {

    private BitbucketUser actor;

    private final JsonObject json;

    public BitbucketRepositoryPush(JsonObject json) {
        this.json = json;
    }

    /**
     * Returns the actor of this repository push.
     *
     * @return {@link BitbucketUser} object for the actor
     */
    public BitbucketUser getActor() {
        return actor;
    }

    public JsonObject getJson() {
        return json;
    }

    /**
     * Sets the actor of this repository push.
     *
     * @param actor {@link BitbucketUser} object for the new actor
     */
    public void setActor(BitbucketUser actor) {
        this.actor = actor;
    }
}
