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
import org.vx68k.bitbucket.api.client.Repository;
import org.vx68k.bitbucket.api.client.User;

/**
 * Represents an activity on a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public abstract class Activity {

    /**
     * JSON key for the actor object.
     */
    protected static final String ACTOR_KEY = "actor";

    /**
     * JSON key for the repository object.
     */
    protected static final String REPOSITORY_KEY = "repository";

    private final JsonObject jsonObject;

    private final User actor;

    private final Repository repository;

    protected Activity(JsonObject json) {
        this.jsonObject = json;
        this.actor = new User(json.getJsonObject(ACTOR_KEY));
        this.repository = new Repository(json.getJsonObject(REPOSITORY_KEY));
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
     * Returns the actor of this object.
     *
     * @return actor of this object
     */
    public User getActor() {
        return actor;
    }

    /**
     * Returns the repository of this object.
     *
     * @return repository of this object
     */
    public Repository getRepository() {
        return repository;
    }
}
