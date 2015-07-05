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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.eclipse.jetty.deploy.App;
import org.eclipse.jetty.deploy.AppProvider;
import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;

/**
 * Web application main class for Jetty.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
public class JettyWebApp extends AbstractLifeCycle implements AppProvider {

    /**
     * Name of the property name for the HTTP port.
     */
    public static final String HTTP_PORT_NAME
            = "org.vx68k.bitbucket.webhook.example.httpPort";

    private static final int DEFAULT_HTTP_PORT = 8080;

    private static final String SERVER_PROPERTIES
            = "META-INF/server.properties";

    private DeploymentManager deploymentManager;

    private App rootApp;

    /**
     * Runs a webhook example application.
     *
     * @exception Exception if any exception occurs
     */
    protected void run() throws Exception {
        Properties serverProperties = new Properties();
        serverProperties.load(
                ClassLoader.getSystemResourceAsStream(SERVER_PROPERTIES));
        System.getProperties().putAll(serverProperties);

        int httpPort = DEFAULT_HTTP_PORT;
        String httpPortProperty = System.getProperty(HTTP_PORT_NAME);
        if (httpPortProperty != null) {
            httpPort = Integer.parseInt(httpPortProperty);
        }

        Server server = new Server(httpPort);

        // Setting up for jetty-annotations.
        Configuration.ClassList classes
                = Configuration.ClassList.setServerDefault(server);
        classes.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        DeploymentManager deploymentManager = new DeploymentManager();
        deploymentManager.setContexts(contexts);
        deploymentManager.setContextAttribute(
                WebInfConfiguration.CONTAINER_JAR_PATTERN,
                ".*\\.jar$|.*/classes/?$");
        deploymentManager.addAppProvider(this);
        server.addBean(deploymentManager);

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

    @Override
    protected void doStart() {
        if (rootApp != null) {
            throw new IllegalStateException("App is already created");
        }

        try {
            rootApp = new App(deploymentManager, this, "META-INF/webapp");
            deploymentManager.addApp(rootApp);
        } catch (RuntimeException e) {
            rootApp = null;
            throw e;
        }
    }

    @Override
    protected void doStop() {
        if (rootApp != null) {
            try {
                deploymentManager.removeApp(rootApp);
            } finally {
                rootApp = null;
            }
        }
    }

    @Override
    public void setDeploymentManager(DeploymentManager deploymentManager) {
        this.deploymentManager = deploymentManager;
    }

    @Override
    public ContextHandler createContextHandler(App app) throws IOException {
        URL appResource = ClassLoader.getSystemResource(app.getOriginId());
        if (appResource == null) {
            throw new FileNotFoundException("Web application not found");
        }

        WebAppContext contextHandler = new WebAppContext();
        // Excluding some from the server classes for Weld to work.
        contextHandler.prependServerClass(
                "-org.eclipse.jetty.server.handler.ContextHandler");
        contextHandler.prependServerClass(
                "-org.eclipse.jetty.servlet.ServletContextHandler");
        contextHandler.prependServerClass(
                "-org.eclipse.jetty.servlet.ServletHandler");

        contextHandler.setWar(appResource.toString());
        return contextHandler;
    }
}
