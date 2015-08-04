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

## Configuring the example web application

The example Java EE web application can simply run anonymously without any
configuration, but you can configure the Bitbucket OAuth client credentials by
Java system properties or by environment variables.
If you configure the client credentials, a Login button will appear at the
top-right corner of the main page and users can test logins with their
Bitbucket accounts.

To configure the client credentials by Java system properties, set
`org.vx68k.bitbucket.webhook.example.clientID` to the client identifier
(consumer key) and `org.vx68k.bitbucket.webhook.example.clientSecret` to the
corresponding client secret (consumer secret).
If you do not set the Java system properties, the values of environment
variables `BITBUCKET_CLIENT_ID` and `BITBUCKET_CLIENT_SECRET` will be used as
the client credentials instead, and the author hopes it will be useful if you
cannot set Java system properties for the web application.
