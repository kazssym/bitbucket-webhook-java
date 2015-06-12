/*
 * LoggingObserver
 * Copyright (C) 2015 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.vx68k.bitbucket;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.vx68k.bitbucket.BitbucketRepositoryPush;

/**
 * Logs the JSON object for each push notification.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
public class LoggingObserver {

    private final Logger logger = Logger.getLogger(getClass().getName());

    public void commitNotified(@Observes BitbucketRepositoryPush push) {
        BitbucketUser actor = push.getActor();
        logger.info(actor.getUsername() + " pushed");
        logger.fine(push.getRawJsonObject().toString());
    }
}
