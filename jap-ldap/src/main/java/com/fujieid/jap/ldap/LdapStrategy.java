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

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.config.AuthenticateConfig;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.exception.JapException;
import com.fujieid.jap.core.result.JapErrorCode;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.core.store.JapUserStore;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.JapHttpResponse;
import com.fujieid.jap.ldap.model.LdapPerson;
import com.fujieid.jap.ldap.template.LdapDefaultTemplate;
import com.fujieid.jap.ldap.template.LdapTemplate;

/**
 * The LDAP authentication strategy uses the LDAP protocol to authenticate users.
 * The following cryptographic algorithms are supported:
 * clear, k5key, md5, smd5, sha, ssha, sha512, sha256, ext_des, md5crypt, sha256crypt, sha512crypt and crypt
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapStrategy extends AbstractJapStrategy {

    public LdapStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    public LdapStrategy(JapUserService japUserService, JapConfig japConfig, JapCache japCache) {
        super(japUserService, japConfig, japCache);
    }

    public LdapStrategy(JapUserService japUserService, JapConfig japConfig, JapUserStore japUserStore, JapCache japCache) {
        super(japUserService, japConfig, japUserStore, japCache);
    }

    @Override
    public JapResponse authenticate(AuthenticateConfig config, JapHttpRequest request, JapHttpResponse response) {
        JapUser sessionUser = this.checkSession(request, response);
        if (null != sessionUser) {
            return JapResponse.success(sessionUser);
        }

        try {
            this.checkAuthenticateConfig(config, LdapConfig.class);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        LdapConfig ldapConfig = (LdapConfig) config;

        String username = request.getParameter(ldapConfig.getUsernameField());
        String password = request.getParameter(ldapConfig.getPasswordField());
        LdapTemplate ldapTemplate = new LdapDefaultTemplate(new LdapDataSource(ldapConfig));
        LdapPerson ldapPerson = null;
        try {
            ldapPerson = ldapTemplate.login(username, password);
        } catch (JapException e) {
            return JapResponse.error(e.getErrorCode(), e.getErrorMessage());
        }
        if (null == ldapPerson) {
            return JapResponse.error(JapErrorCode.LOGIN_FAILURE);
        }
        JapUser japUser = this.japUserService.createAndGetLdapUser(ldapPerson);
        if (null == japUser) {
            return JapResponse.error(JapErrorCode.UNABLE_SAVE_USERINFO);
        }
        return this.loginSuccess(japUser, request, response);
    }
}
