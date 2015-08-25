/*
 * Activity - represents an activity on a Bitbucket repository
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

import javax.json.JsonObject;

/**
 * Represents an activity on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public abstract class Activity {

    protected static final String ACTOR_KEY = "actor";

    private final JsonObject jsonObject;
    private final BitbucketUser actor;

    protected Activity(JsonObject jsonObject) {
        this.jsonObject = jsonObject;

        JsonObject actorObject = jsonObject.getJsonObject(ACTOR_KEY);
        actor = BitbucketUser.fromJsonObject(actorObject);
    }

    /**
     * Returns the raw JSON object for this push.
     *
     * @return JSON object
     */
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Returns the actor of this push.
     *
     * @return {@link BitbucketUser} object for the actor
     */
    public BitbucketUser getActor() {
        return actor;
    }
}
