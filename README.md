# READ ME

This directory contains the source code for the Bitbucket Webhook Library for
Java.

The Bitbucket Webhook Servlet Library provides a servlet that handles HTTP
requests from [Bitbucket][] webhooks by notifying other classes of them using
[CDI][] events.

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

 * webhook – core library,
 * example – example web application that uses the library,
 * example-servlet – the same web application for Servlet-only containers, and
 * example-standalone – the same web application packaged as an executable jar
   file with [Jetty][].

[Jetty]: <https://www.eclipse.org/jetty/>

## Configuring the example web application

The example web application can be configured with Bitbucket OAuth client
credentials by Java system properties or by environment variables.
If is is configured with the client credentials, a Login item will appear at
the top-right corner of the web pages and users can log in with their
Bitbucket accounts, though they can do nothing but log in.

To configure the client credentials by Java system properties, set
`org.vx68k.bitbucket.webhook.example.oauth.id` to the client identifier
(consumer key) and `org.vx68k.bitbucket.webhook.example.oauth.secret` to the
corresponding client secret (consumer secret).
If you do not set the Java system properties, the values of environment
variables `BITBUCKET_OAUTH_CLIENT_ID` and `BITBUCKET_OAUTH_CLIENT_SECRET` will
be used as the client credentials instead, and they would be useful if you
cannot set Java system properties for the web application.
