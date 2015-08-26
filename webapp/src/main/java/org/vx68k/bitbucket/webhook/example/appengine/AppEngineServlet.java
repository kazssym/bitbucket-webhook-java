/*
 * AppEngineServlet
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

package org.vx68k.bitbucket.webhook.example.appengine;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that handles App Engine management requests.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@WebServlet(name = "App Engine Servlet", urlPatterns = {"/_ah/*"})
public class AppEngineServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");

        PrintWriter writer = response.getWriter();
        writer.println("OK");
        writer.close();
    }
}
