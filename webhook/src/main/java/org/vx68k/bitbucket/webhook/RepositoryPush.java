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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Represents a push to a Bitbucket repository.
 * @author Kaz Nishimura
 * @since 1.0
 */
public class RepositoryPush extends Activity {

    private static final Logger logger =
            Logger.getLogger(RepositoryPush.class.getPackage().getName());

    private final JsonObject pushJsonObject;

    private List<Change> changes;

    /**
     * Constructs this object from a JSON object.
     *
     * @param json JSON object
     */
    public RepositoryPush(JsonObject json) {
        super(json);

        JsonObject push = json.getJsonObject(JsonKeys.PUSH);
        changes = parseChanges(push.getJsonArray(JsonKeys.CHANGES));

        pushJsonObject = push;
    }

    public JsonObject getPushJsonObject() {
        return pushJsonObject;
    }

    /**
     * Returns the list of the changes of this object.
     * @return list of the changes
     */
    public List<Change> getChanges() {
        return changes;
    }

    /**
     * Sets the list of the changes of this object.
     * @param changes list of the change
     */
    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }

    /**
     * Parses a JSON array to a list of changes.
     * @param json JSON array that represents list of changes
     * @return list of changes
     */
    protected List<Change> parseChanges(JsonArray json) {
        List<Change> changes = new ArrayList<Change>();
        for (JsonValue value : json) {
            changes.add(new Change((JsonObject) value));
        }
        return changes;
    }

    /**
     * Change of a Bitbucket repository.
     * @since 4.0
     */
    public static class Change {

        public Change() {
            logger.finer("Creating a blank Change");
        }

        public Change(JsonObject json) {
            logger.log(
                    Level.INFO, "Parsing JSON object (change): {0}", json);
        }
    }
}
