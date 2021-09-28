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

/**
 * Constant class associated with user attribute names in LDAP
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public interface InetOrgPersonConst {
    String SN = "sn";
    String CN = "cn";
    String USER_PASSWORD = "userPassword";
    String TELEPHONE_NUMBER = "telephoneNumber";
    String DESCRIPTION = "description";
    String DISPLAY_NAME = "displayName";
    String GIVEN_NAME = "givenName";
    String UID = "uid";
    String UID_NUMBER = "uidNumber";
    String GID_NUMBER = "gidNumber";
    String MAIL = "mail";
    String MOBILE = "mobile";
    String TITLE = "title";
}
