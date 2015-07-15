/*
 * WebAppUser
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

package org.vx68k.bitbucket.webhook.example;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User of this web application.
 *
 * @author Kaz Nishimura
 * @since 1.0
 */
@SessionScoped
@Named("user")
public class WebAppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private WebAppConfig config;

    private boolean authenticated = false;

    public WebAppUser() {
    }

    public WebAppUser(WebAppConfig config) {
        this.config = config;
    }

    public WebAppConfig getConfig() {
        return config;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    @Inject
    public void setConfig(WebAppConfig config) {
        this.config = config;
    }

    public String login() {
        return "unimplemented";
    }
}
