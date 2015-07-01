/*
 * JettyWebApp - web application main class for Jetty
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

package org.vx68k.bitbucket.webhook.example.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Web application main class for Jetty.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class JettyWebApp {

    private static final int DEFAULT_HTTP_PORT = 8080;

    /**
     * Name of the property name for the HTTP port.
     */
    public static final String HTTP_PORT_NAME
            = "org.vx68k.bitbucket.webhook.example.httpPort";

    /**
     * Runs a webhook example application.
     *
     * @exception Exception if any exception occurs
     */
    protected void run() throws Exception {
        int httpPort = DEFAULT_HTTP_PORT;
        String httpPortProperty = System.getProperty(HTTP_PORT_NAME);
        if (httpPortProperty != null) {
            httpPort = Integer.parseInt(httpPortProperty);
        }

        Server server = new Server(httpPort);
        Configuration.ClassList classes
                = Configuration.ClassList.setServerDefault(server);
        classes.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        WebAppContext context = new WebAppContext();
        context.setParentLoaderPriority(true);
        context.setWar(ClassLoader.getSystemResource("META-INF/webapp").toString());
        context.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/.*\\.jar$");
        server.setHandler(context);

        server.start();
        server.join();
    }

    /**
     * Runs a webhook example application.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        try {
            new JettyWebApp().run();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
