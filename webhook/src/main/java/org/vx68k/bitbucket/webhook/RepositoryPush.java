/*
 * RepositoryPush - represents a push to a Bitbucket repository
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
 * @author Kaz Nishimura
 * @since 1.0
 */
public class RepositoryPush extends Activity {

    private final JsonObject pushJsonObject;

    /**
     * Constructs this object from a JSON object.
     *
     * @param jsonObject JSON object
     */
    public RepositoryPush(JsonObject jsonObject) {
        super(jsonObject);

        pushJsonObject = jsonObject.getJsonObject(JsonKeys.PUSH);
    }

    public JsonObject getPushJsonObject() {
        return pushJsonObject;
    }
}
