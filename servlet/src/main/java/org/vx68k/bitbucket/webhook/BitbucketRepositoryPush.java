/*
 * BitbucketRepositoryPush - represents a push to a Bitbucket repository
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
 * Represents a push to a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketRepositoryPush {

    private static final String ACTOR_KEY = "actor";

    /**
     * JSON key for the push object.
     */
    public static final String PUSH_KEY = "push";

    private BitbucketUser actor;

    private JsonObject json;

    private JsonObject rawJsonObject;

    /**
     * Returns the actor of this push.
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
     * Returns the raw JSON object for this push.
     *
     * @return JSON object
     */
    public JsonObject getRawJsonObject() {
        return rawJsonObject;
    }

    /**
     * Sets the actor of this push.
     *
     * @param actor {@link BitbucketUser} object for the new actor
     */
    public void setActor(BitbucketUser actor) {
        this.actor = actor;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    /**
     * Sets the raw JSON object for this push.
     * @param rawJsonObject
     */
    public void setRawJsonObject(JsonObject rawJsonObject) {
        this.rawJsonObject = rawJsonObject;
    }

    /**
     * Creates a {@link BitbucketRepositoryPush} object from a JSON object.
     *
     * @param object JSON object
     * @return parsed {@link BitbucketRepositoryPush} object
     * @exception NullPointerException if any required fields are missing
     */
    public static BitbucketRepositoryPush fromJsonObject(JsonObject object) {
        BitbucketRepositoryPush push = new BitbucketRepositoryPush();
        JsonObject actorObject = object.getJsonObject(ACTOR_KEY);
        push.setActor(BitbucketUser.fromJsonObject(actorObject));
        push.setJson(object.getJsonObject(PUSH_KEY));
        push.setRawJsonObject(object);
        return push;
    }
}
