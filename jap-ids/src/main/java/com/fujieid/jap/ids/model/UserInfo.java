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
package com.fujieid.jap.ids.model;

import com.xkcoding.json.util.StringUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * User info
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims" target="_blank">Standard Claims</a>
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#UserInfoResponse" target="_blank">UserInfo Response</a>
 * @see <a href="https://openid.net/specs/openid-connect-core-1_0.html#IDToken" target="_blank">ID Token</a>
 * @since 1.0.1
 */
public class UserInfo implements Serializable {
    /**
     * string Subject - Identifier for the End-User at the Issuer.
     */
    private String id;
    /**
     * string	Subject - Identifier for the End-User at the Issuer.
     */
    private String sub;
    /**
     * string End-User's full name in displayable form including all name parts, possibly including titles and suffixes,
     * ordered according to the End-User's locale and preferences.
     */
    private String name;
    /**
     * string End-User's full name in displayable form including all name parts, possibly including titles and suffixes,
     * ordered according to the End-User's locale and preferences.
     */
    private String username;
    /**
     * string Given name(s) or first name(s) of the End-User. Note that in some cultures, people can have multiple given names;
     * all can be present, with the names being separated by space characters.
     */
    private String given_name;
    /**
     * string Surname(s) or last name(s) of the End-User. Note that in some cultures, people can have multiple family names or no family name;
     * all can be present, with the names being separated by space characters.
     */
    private String family_name;
    /**
     * string Middle name(s) of the End-User. Note that in some cultures, people can have multiple middle names;
     * all can be present, with the names being separated by space characters. Also note that in some cultures, middle names are not used.
     */
    private String middle_name;
    /**
     * string Casual name of the End-User that may or may not be the same as the given_name. For instance,
     * a nickname value of Mike might be returned alongside a given_name value of Michael.
     */
    private String nickname;
    /**
     * string Shorthand name by which the End-User wishes to be referred to at the RP, such as janedoe or j.doe.
     * This value MAY be any valid JSON string including special characters such as @, /, or whitespace.
     * The RP MUST NOT rely upon this value being unique, as discussed in Section 5.7.
     */
    private String preferred_username;
    /**
     * string URL of the End-User's profile page. The contents of this Web page SHOULD be about the End-User.
     */
    private String profile;
    /**
     * string URL of the End-User's profile picture. This URL MUST refer to an image file (for example, a PNG, JPEG,
     * or GIF image file), rather than to a Web page containing an image.
     * Note that this URL SHOULD specifically reference a profile photo of the End-User suitable for displaying when describing the End-User,
     * rather than an arbitrary photo taken by the End-User.
     */
    private String picture;
    /**
     * string URL of the End-User's Web page or blog. This Web page SHOULD contain information published by the End-User
     * or an organization that the End-User is affiliated with.
     */
    private String website;
    /**
     * string End-User's preferred e-mail address. Its value MUST conform to the RFC 5322 [RFC5322] addr-spec syntax.
     * The RP MUST NOT rely upon this value being unique, as discussed in Section 5.7.
     */
    private String email;
    /**
     * boolean True if the End-User's e-mail address has been verified; otherwise false. When this Claim Value is true,
     * this means that the OP took affirmative steps to ensure that this e-mail address was controlled by the End-User at the time the verification was performed.
     * The means by which an e-mail address is verified is context-specific, and dependent upon the trust framework or contractual agreements within which the parties are operating.
     */
    private String email_verified;
    /**
     * string End-User's gender. Values defined by this specification are female and male.
     * Other values MAY be used when neither of the defined values are applicable.
     */
    private String gender;
    /**
     * string End-User's birthday, represented as an ISO 8601:2004 [ISO8601‑2004] YYYY-MM-DD format. The year MAY be 0000,
     * indicating that it is omitted. To represent only the year, YYYY format is allowed.
     * Note that depending on the underlying platform's date related function, providing just year can result in varying month and day,
     * so the implementers need to take this factor into account to correctly process the dates.
     */
    private String birthdate;
    /**
     * string String from zoneinfo [zoneinfo] time zone database representing the End-User's time zone. For example,
     * Europe/Paris or America/Los_Angeles.
     */
    private String zoneinfo;
    /**
     * string End-User's locale, represented as a BCP47 [RFC5646] language tag.
     * This is typically an ISO 639-1 Alpha-2 [ISO639‑1] language code in lowercase and an ISO 3166-1 Alpha-2 [ISO3166‑1] country code in uppercase,
     * separated by a dash. For example, en-US or fr-CA. As a compatibility note, some implementations have used an underscore as the separator rather than a dash,
     * for example, en_US; Relying Parties MAY choose to accept this locale syntax as well.
     */
    private String locale;
    /**
     * string End-User's preferred telephone number. E.164 [E.164] is RECOMMENDED as the format of this Claim,
     * for example, +1 (425) 555-1212 or +56 (2) 687 2400. If the phone number contains an extension,
     * it is RECOMMENDED that the extension be represented using the RFC 3966 [RFC3966] extension syntax,
     * for example, +1 (604) 555-1234;ext=5678.
     */
    private String phone_number;
    /**
     * boolean True if the End-User's phone number has been verified; otherwise false. When this Claim Value is true,
     * this means that the OP took affirmative steps to ensure that this phone number was controlled by the End-User at the time the verification was performed.
     * The means by which a phone number is verified is context-specific, and dependent upon the trust framework or contractual agreements within which the parties are operating.
     * When true, the phone_number Claim MUST be in E.164 format and any extensions MUST be represented in RFC 3966 format.
     */
    private String phone_number_verified;
    /**
     * JSON object End-User's preferred postal address. The value of the address member is a JSON [RFC4627] structure containing some or all of the members defined in Section 5.1.1.
     */
    private Map<String, String> address;
    private String updated_at;

    public String getId() {
        return id;
    }

    public UserInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getSub() {
        return StringUtil.isEmpty(sub) ? getId() : sub;
    }

    public UserInfo setSub(String sub) {
        this.sub = sub;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getGiven_name() {
        return given_name;
    }

    public UserInfo setGiven_name(String given_name) {
        this.given_name = given_name;
        return this;
    }

    public String getFamily_name() {
        return family_name;
    }

    public UserInfo setFamily_name(String family_name) {
        this.family_name = family_name;
        return this;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public UserInfo setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfo setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getPreferred_username() {
        return preferred_username;
    }

    public UserInfo setPreferred_username(String preferred_username) {
        this.preferred_username = preferred_username;
        return this;
    }

    public String getProfile() {
        return profile;
    }

    public UserInfo setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public UserInfo setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public UserInfo setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public UserInfo setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserInfo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public UserInfo setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public String getZoneinfo() {
        return zoneinfo;
    }

    public UserInfo setZoneinfo(String zoneinfo) {
        this.zoneinfo = zoneinfo;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public UserInfo setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public UserInfo setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getPhone_number_verified() {
        return phone_number_verified;
    }

    public UserInfo setPhone_number_verified(String phone_number_verified) {
        this.phone_number_verified = phone_number_verified;
        return this;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public UserInfo setAddress(Map<String, String> address) {
        this.address = address;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserInfo setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }
}
