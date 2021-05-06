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
package com.fujieid.jap.ids.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The relationship table between scope and user attributes. For a specific scope, only part of the user attributes can be obtained
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#ScopeClaims" target="_blank">5.4.  Requesting Claims using Scope Values</a>
 * @since 1.0.2
 */
public enum ScopeClaimsMapping {

    // This scope value requests access to the End-User's default profile Claims,
    // which are: name, family_name, given_name, middle_name, nickname, preferred_username, profile, picture, website, gender, birthdate, zoneinfo, locale, and updated_at.
    profile(Arrays.asList("name", "family_name", "given_name", "middle_name", "nickname", "preferred_username", "profile", "picture", "website", "gender", "birthdate", "zoneinfo", "locale", "and updated_at")),
    // This scope value requests access to the email and email_verified Claims.
    email(Arrays.asList("email", "email_verified")),
    // This scope value requests access to the phone_number and phone_number_verified Claims.
    phone(Arrays.asList("phone_number", "phone_number_verified")),
    // This scope value requests access to the address Claim.
    address(Collections.singletonList("address"));


    private final List<String> claims;

    ScopeClaimsMapping(List<String> claims) {
        this.claims = claims;
    }

    public List<String> getClaims() {
        return claims;
    }
}
