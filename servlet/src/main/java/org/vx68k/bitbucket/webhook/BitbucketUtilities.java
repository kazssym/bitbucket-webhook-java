/*
 + BitbucketUtilities - utility methods for Bitbucket Webhook Servlet
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

import java.util.UUID;

/**
 * Collection of utility methods for the Bitbucket Webhook Servlet.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class BitbucketUtilities {

    /**
     * Parses a string into a UUID.
     * The string representation of a UUID may be enclosed in braces.
     *
     * @param uuidString {@link String} which represents a UUID
     * @return parsed {@link UUID} object, or <code>null</code> if uuidString
     * is <code>null</code>
     */
    public static UUID parseUuid(String uuidString) {
        if (uuidString == null) {
            return null;
        }

        if (uuidString.startsWith("{") && uuidString.endsWith("}")) {
            uuidString = uuidString.substring(1, uuidString.length() - 1);
        }
        return UUID.fromString(uuidString);
    }
}
