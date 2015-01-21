/*
 * BitbucketCommitNotification
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

package org.vx68k.bitbucket.servlet;

import javax.json.JsonObject;

/**
 * Commit (push) notification from Bitbucket.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketCommitNotification {

    private final JsonObject json;

    public BitbucketCommitNotification(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }
}
