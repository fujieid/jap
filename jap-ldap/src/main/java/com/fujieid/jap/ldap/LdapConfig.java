/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.ldap;

import com.fujieid.jap.core.annotation.NotEmpty;
import com.fujieid.jap.core.config.AuthenticateConfig;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapConfig extends AuthenticateConfig {
    /**
     * LDAP data source URL, such as ldap://localhost:389
     */
    @NotEmpty(message = "LDAP data source URL cannot be empty.")
    private String url;
    /**
     * LDAP user name, such as: cn=admin,dc=example,dc=org
     */
    @NotEmpty(message = "LDAP bindDn cannot be empty.")
    private String bindDn;
    /**
     * LDAP administrator password
     */
    @NotEmpty(message = "LDAP administrator password cannot be empty")
    private String credentials;
    /**
     * Basic catalog
     */
    @NotEmpty(message = "LDAP Basic catalog cannot be empty")
    private String baseDn;
    /**
     * Query conditions, such as: (&(objectClass=organizationalPerson)(uid=%s))
     */
    private String filters = "(&(objectClass=inetOrgPerson)(uid=%s))";
    /**
     * This parameter must be configured when using ldaps, which means a trusted certificate
     */
    private String trustStore;
    /**
     * {@link LdapConfig#trustStore} password
     */
    private String trustStorePassword;

    /**
     * Get the user name from request through {@code request.getParameter(`usernameField`)}, which defaults to "username"
     */
    private String usernameField = "username";
    /**
     * Get the password from request through {@code request.getParameter(`passwordField`)}, which defaults to "password"
     */
    private String passwordField = "password";

    public String getUrl() {
        return url;
    }

    public LdapConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBindDn() {
        return bindDn;
    }

    public LdapConfig setBindDn(String bindDn) {
        this.bindDn = bindDn;
        return this;
    }

    public String getCredentials() {
        return credentials;
    }

    public LdapConfig setCredentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public LdapConfig setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }

    public String getFilters() {
        return filters;
    }

    public LdapConfig setFilters(String filters) {
        this.filters = filters;
        return this;
    }

    public String getTrustStore() {
        return trustStore;
    }

    public LdapConfig setTrustStore(String trustStore) {
        this.trustStore = trustStore;
        return this;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public LdapConfig setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
        return this;
    }

    public String getUsernameField() {
        return usernameField;
    }

    public LdapConfig setUsernameField(String usernameField) {
        this.usernameField = usernameField;
        return this;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public LdapConfig setPasswordField(String passwordField) {
        this.passwordField = passwordField;
        return this;
    }
}
