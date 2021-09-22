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
 * The login verification is carried out through the HTTP API interface.
 * Mainly divided into three methods: BASIC, DIGEST and BEARER.
 * <p>
 * 1. <strong>Basic</strong> authentication is the simplest HTTP authentication method. The authentication process is simple and clear.
 * The plain text password is sent directly during the authentication process, which can easily lead to password leakage
 * and is suitable for systems with low security requirements.
 * <p>
 * 2. <strong>Digest</strong> authentication is to make up for the weaknesses of BASIC authentication.
 * It uses a nonce random number string, and the two parties agree on what information to
 * hash to complete the verification of the identity of both parties.
 * <p>
 * 3. <strong>Bearer</strong> authentication can also be called Bearer Token authentication.
 * The JWT we often use is a Bearer Token authentication method. Token is the core of Bearer authentication,
 * and the server performs authentication and authorization by verifying the legitimacy of the Token.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
package com.fujieid.jap.httpapi;
