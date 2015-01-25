/*
 * BitbucketPushServlet - gets notified of a push to a repository
 * Copyright (C) 2014-2015 Kaz Nishimura
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

import java.io.IOException;
import java.io.StringReader;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gets notified when a user makes a push to a Bitbucket repository.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@WebServlet(name = "Bitbucket Push Servlet", urlPatterns = {"/notify"})
public class BitbucketPushServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private Event<BitbucketPushNotification> notificationEvent;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        JsonReader reader = Json.createReader(
                new StringReader(request.getParameter("payload")));
        try {
            JsonObject json = reader.readObject();
            notificationEvent.fire(new BitbucketPushNotification(json));
        } catch (JsonParsingException t) {
            log("JSON parsing error", t);
        }
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // TODO:
        response.getWriter().close(); // TODO:
    }
}
