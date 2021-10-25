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
package com.fujieid.jap.ldap.model;

import java.util.Map;

/**
 * User entity in LDAP
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapPerson {
    protected String uid;
    protected String uidNumber;
    protected String gidNumber;
    protected String cn;
    protected String sn;
    protected String dn;
    protected String password;
    protected String givenName;
    protected String displayName;
    protected String title;
    protected String description;
    protected String mail;
    protected String telephoneNumber;
    protected String mobile;
    /**
     * Full attributes of the user in LDAP
     */
    private Map<String, String> attributes;

    public String getUid() {
        return uid;
    }

    public LdapPerson setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public LdapPerson setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
        return this;
    }

    public String getGidNumber() {
        return gidNumber;
    }

    public LdapPerson setGidNumber(String gidNumber) {
        this.gidNumber = gidNumber;
        return this;
    }

    public String getCn() {
        return cn;
    }

    public LdapPerson setCn(String cn) {
        this.cn = cn;
        return this;
    }

    public String getSn() {
        return sn;
    }

    public LdapPerson setSn(String sn) {
        this.sn = sn;
        return this;
    }

    public String getDn() {
        return dn;
    }

    public LdapPerson setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LdapPerson setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public LdapPerson setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LdapPerson setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public LdapPerson setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LdapPerson setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public LdapPerson setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public LdapPerson setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public LdapPerson setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public LdapPerson setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
        return this;
    }
}
