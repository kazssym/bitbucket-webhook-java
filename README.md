# READ ME

This directory contains the source code for the Bitbucket Webhook Servlet.

The Bitbucket Webhook Servlet handles HTTP requests from [Bitbucket][]
webhooks by notifying other application components of them using [CDI][]
events.

This program is *[free software][]*: you can redistribute it and/or modify it
under the terms of the *[GNU Affero General Public License][]* as published by
the [Free Software Foundation][], either version 3 of the License, or (at your
option) any later version.

[Bitbucket]: <https://bitbucket.org/>
[CDI]: <https://jcp.org/en/jsr/detail?id=299> "JSR 299: Contexts and Dependency Injection for the Java EE platform"
[Free software]: <http://www.gnu.org/philosophy/free-sw.html> "What is free software?"
[GNU Affero General Public License]: <http://www.gnu.org/licenses/agpl.html>
[Free Software Foundation]: <http://www.fsf.org/>

## Modules

The parent Maven project consists of the following modules:

 * servlet – main servlet implementation,
 * webapp – example Java EE web application that uses the servlet,
 * webapp-servlet – the same web application for Servlet-only containers, and
 * webapp-standalone – the same web application packaged as an executable jar
   file with [Jetty][].

[Jetty]: <https://www.eclipse.org/jetty/>
