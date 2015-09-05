/*
 * ActivityLog
 * Copyright (C) 2015 Nishimura Software Studio
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

package org.vx68k.bitbucket.webhook.example;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import org.vx68k.bitbucket.api.client.User;
import org.vx68k.bitbucket.webhook.RepositoryPush;

/**
 * Logs the JSON object for each push notification.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@ApplicationScoped
@Named("activityLog")
public class ActivityLog {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private RepositoryPush lastRepositoryPush = null;

    public void handleRepositoryPush(@Observes RepositoryPush push) {
        User actor = push.getActor();
        logger.info(actor.getUsername() + " pushed");

        lastRepositoryPush = push;
    }

    public RepositoryPush getLastRepositoryPush() {
        return lastRepositoryPush;
    }
}
