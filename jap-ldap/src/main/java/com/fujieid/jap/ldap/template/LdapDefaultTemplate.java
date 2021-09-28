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
package com.fujieid.jap.ldap.template;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fujieid.jap.ldap.LdapConfig;
import com.fujieid.jap.ldap.LdapDataSource;
import com.fujieid.jap.ldap.LdapUtil;
import com.fujieid.jap.ldap.model.LdapPerson;
import com.fujieid.jap.ldap.password.LdapPasswordMatch;
import com.fujieid.jap.ldap.password.LdapPasswordMatchFactory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapDefaultTemplate implements LdapTemplate {
    private static final Log log = LogFactory.get();
    private final LdapDataSource ldapDataSource;
    private final LdapConfig ldapConfig;

    public LdapDefaultTemplate(LdapDataSource ldapDataSource) {
        this.ldapDataSource = ldapDataSource;
        this.ldapConfig = this.ldapDataSource.getLdapConfig();
    }

    private NamingEnumeration<SearchResult> searchResult(String userName) {
        DirContext dirContext = ldapDataSource.openConnection();
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String filter = String.format(this.ldapConfig.getFilters(), userName);
        try {
            return dirContext.search(this.ldapConfig.getBaseDn(), filter, constraints);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LdapPerson findPerson(String userName) {
        try {
            NamingEnumeration<SearchResult> results = searchResult(userName);
            while (null != results && results.hasMore()) {
                SearchResult searchResult = results.nextElement();
                if (searchResult != null) {
                    log.debug("name " + searchResult.getName());
                    log.debug("NameInNamespace " + searchResult.getNameInNamespace());
                    log.debug("Attributes " + searchResult.getAttributes());
                    LdapPerson person = LdapUtil.convertToPerson(searchResult);
                    if (person.getCn() != null) {
                        return person;
                    }
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean login(String userName, String password) {
        LdapPerson ldapPerson = this.findPerson(userName);
        LdapPasswordMatch ldapPasswordMatch = LdapPasswordMatchFactory.getMatcherByPassword(ldapPerson.getPassword());
        return ldapPasswordMatch.matches(password, ldapPerson.getPassword());
    }
}
