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
/**
 * To provide processing methods for business in OAuth authorization process, the usage is as follows:
 * <p>
 * {@link com.fujieid.jap.oauth2.token.AccessTokenHelper#getToken(HttpServletRequest, OAuthConfig)}
 * According to the parameters in {@link com.fujieid.jap.oauth2.OAuthConfig}, determine which authorization mode token data to obtain
 * <p>
 * For OAuth's grant type, the following private methods are provided:
 * <p>
 * 1. {@code getAccessTokenOfAuthorizationCodeMode(HttpServletRequest, OAuthConfig)} - Authorization Code Grant
 * <p>
 * 2. {@code getAccessTokenOfImplicitMode(HttpServletRequest)} - Implicit Grant
 * <p>
 * 3. {@code getAccessTokenOfPasswordMode(HttpServletRequest, OAuthConfig)} - Resource Owner Password Credentials Grant
 * <p>
 * 4. {@code getAccessTokenOfClientMode(HttpServletRequest, OAuthConfig)} - Client Credentials Grant
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://tools.ietf.org/html/rfc7636#section-6.1" target="_blank">6.1.  OAuth Parameters Registry</a>
 * @since 1.0.0
 */
package com.fujieid.jap.oauth2.token;

import com.fujieid.jap.oauth2.OAuthConfig;

import javax.servlet.http.HttpServletRequest;
