/*
 * OAuthRedirectionEndpointServlet
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

package org.vx68k.bitbucket.webhook.example.servlet;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.vx68k.bitbucket.webhook.example.WebAppUser;

/**
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@WebServlet(
        name = "OAuth Redirection Endpoint Servlet",
        urlPatterns = {"/authorized/*"})
public class OAuthRedirectionEndpointServlet extends HttpServlet {

    private WebAppUser user;

    @Inject
    public void setUser(WebAppUser user) {
        this.user = user;
    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null
                || !request.getParameter("state").equals(session.getId())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            user.requestToken(request.getParameter("code"));

            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
