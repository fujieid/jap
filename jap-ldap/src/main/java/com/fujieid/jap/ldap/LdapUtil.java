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

import com.fujieid.jap.ldap.model.InetOrgPersonConst;
import com.fujieid.jap.ldap.model.LdapPerson;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.HashMap;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapUtil {

    public static String getAttrStringValue(String attribute, HashMap<String, Attribute> attributeMap) throws NamingException {
        if (attributeMap.containsKey(attribute)) {
            Attribute attr = attributeMap.get(attribute);
            return "userPassword".equals(attribute) ? (new String((byte[]) attr.get())) : attr.get().toString();
        } else {
            return "";
        }
    }

    public static LdapPerson convertToPerson(SearchResult searchResult) {
        LdapPerson userInfo = new LdapPerson();
        try {
            // In naming systems for which the notion of full name does not
            // apply to this binding an <tt>UnsupportedOperationException</tt> is thrown.
            userInfo.setDn(searchResult.getNameInNamespace());
        } catch (Exception e) {
            userInfo.setDn(searchResult.getName());
        }

        Attributes attributes = searchResult.getAttributes();
        userInfo.setAttributes(attributes);

        NamingEnumeration<? extends Attribute> attrs = attributes.getAll();
        HashMap<String, Attribute> attributeMap = new HashMap<>(30);
        while (null != attrs && attrs.hasMoreElements()) {
            Attribute objAttrs = attrs.nextElement();
            attributeMap.put(objAttrs.getID(), objAttrs);
        }
        try {
            userInfo.setUid(getAttrStringValue(InetOrgPersonConst.UID, attributeMap));
            userInfo.setUidNumber(getAttrStringValue(InetOrgPersonConst.UID_NUMBER, attributeMap));
            userInfo.setGidNumber(getAttrStringValue(InetOrgPersonConst.GID_NUMBER, attributeMap));
            userInfo.setCn(getAttrStringValue(InetOrgPersonConst.CN, attributeMap));
            userInfo.setSn(getAttrStringValue(InetOrgPersonConst.SN, attributeMap));
            userInfo.setPassword(getAttrStringValue(InetOrgPersonConst.USER_PASSWORD, attributeMap));
            userInfo.setGivenName(getAttrStringValue(InetOrgPersonConst.GIVEN_NAME, attributeMap));
            userInfo.setDisplayName(getAttrStringValue(InetOrgPersonConst.DISPLAY_NAME, attributeMap));
            userInfo.setTitle(getAttrStringValue(InetOrgPersonConst.TITLE, attributeMap));
            userInfo.setDescription(getAttrStringValue(InetOrgPersonConst.DESCRIPTION, attributeMap));
            userInfo.setMail(getAttrStringValue(InetOrgPersonConst.MAIL, attributeMap));
            userInfo.setTelephoneNumber(getAttrStringValue(InetOrgPersonConst.TELEPHONE_NUMBER, attributeMap));
            userInfo.setMobile(getAttrStringValue(InetOrgPersonConst.MOBILE, attributeMap));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
