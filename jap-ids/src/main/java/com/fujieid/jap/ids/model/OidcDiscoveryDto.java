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

import java.io.Serializable;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class OidcDiscoveryDto implements Serializable {

    private String issuer;
    private String authorization_endpoint;
    private String token_endpoint;
    private String userinfo_endpoint;
    private String registration_endpoint;
    private String end_session_endpoint;
    private String check_session_iframe;
    private String jwks_uri;
    private List<String> grant_types_supported;
    private List<String> response_modes_supported;
    private List<String> response_types_supported;
    private List<String> scopes_supported;
    private List<String> token_endpoint_auth_methods_supported;
    private List<String> request_object_signing_alg_values_supported;
    private List<String> userinfo_signing_alg_values_supported;
    private boolean request_parameter_supported;
    private boolean request_uri_parameter_supported;
    private boolean require_request_uri_registration;
    private boolean claims_parameter_supported;
    private List<String> id_token_signing_alg_values_supported;
    private List<String> subject_types_supported;
    private List<String> claims_supported;

    public String getIssuer() {
        return issuer;
    }

    public OidcDiscoveryDto setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getAuthorization_endpoint() {
        return authorization_endpoint;
    }

    public OidcDiscoveryDto setAuthorization_endpoint(String authorization_endpoint) {
        this.authorization_endpoint = authorization_endpoint;
        return this;
    }

    public String getToken_endpoint() {
        return token_endpoint;
    }

    public OidcDiscoveryDto setToken_endpoint(String token_endpoint) {
        this.token_endpoint = token_endpoint;
        return this;
    }

    public String getUserinfo_endpoint() {
        return userinfo_endpoint;
    }

    public OidcDiscoveryDto setUserinfo_endpoint(String userinfo_endpoint) {
        this.userinfo_endpoint = userinfo_endpoint;
        return this;
    }

    public String getRegistration_endpoint() {
        return registration_endpoint;
    }

    public OidcDiscoveryDto setRegistration_endpoint(String registration_endpoint) {
        this.registration_endpoint = registration_endpoint;
        return this;
    }

    public String getEnd_session_endpoint() {
        return end_session_endpoint;
    }

    public OidcDiscoveryDto setEnd_session_endpoint(String end_session_endpoint) {
        this.end_session_endpoint = end_session_endpoint;
        return this;
    }

    public String getCheck_session_iframe() {
        return check_session_iframe;
    }

    public OidcDiscoveryDto setCheck_session_iframe(String check_session_iframe) {
        this.check_session_iframe = check_session_iframe;
        return this;
    }

    public String getJwks_uri() {
        return jwks_uri;
    }

    public OidcDiscoveryDto setJwks_uri(String jwks_uri) {
        this.jwks_uri = jwks_uri;
        return this;
    }

    public List<String> getGrant_types_supported() {
        return grant_types_supported;
    }

    public OidcDiscoveryDto setGrant_types_supported(List<String> grant_types_supported) {
        this.grant_types_supported = grant_types_supported;
        return this;
    }

    public List<String> getResponse_modes_supported() {
        return response_modes_supported;
    }

    public OidcDiscoveryDto setResponse_modes_supported(List<String> response_modes_supported) {
        this.response_modes_supported = response_modes_supported;
        return this;
    }

    public List<String> getResponse_types_supported() {
        return response_types_supported;
    }

    public OidcDiscoveryDto setResponse_types_supported(List<String> response_types_supported) {
        this.response_types_supported = response_types_supported;
        return this;
    }

    public List<String> getScopes_supported() {
        return scopes_supported;
    }

    public OidcDiscoveryDto setScopes_supported(List<String> scopes_supported) {
        this.scopes_supported = scopes_supported;
        return this;
    }

    public List<String> getToken_endpoint_auth_methods_supported() {
        return token_endpoint_auth_methods_supported;
    }

    public OidcDiscoveryDto setToken_endpoint_auth_methods_supported(List<String> token_endpoint_auth_methods_supported) {
        this.token_endpoint_auth_methods_supported = token_endpoint_auth_methods_supported;
        return this;
    }

    public List<String> getRequest_object_signing_alg_values_supported() {
        return request_object_signing_alg_values_supported;
    }

    public OidcDiscoveryDto setRequest_object_signing_alg_values_supported(List<String> request_object_signing_alg_values_supported) {
        this.request_object_signing_alg_values_supported = request_object_signing_alg_values_supported;
        return this;
    }

    public List<String> getUserinfo_signing_alg_values_supported() {
        return userinfo_signing_alg_values_supported;
    }

    public OidcDiscoveryDto setUserinfo_signing_alg_values_supported(List<String> userinfo_signing_alg_values_supported) {
        this.userinfo_signing_alg_values_supported = userinfo_signing_alg_values_supported;
        return this;
    }

    public boolean isRequest_parameter_supported() {
        return request_parameter_supported;
    }

    public OidcDiscoveryDto setRequest_parameter_supported(boolean request_parameter_supported) {
        this.request_parameter_supported = request_parameter_supported;
        return this;
    }

    public boolean isRequest_uri_parameter_supported() {
        return request_uri_parameter_supported;
    }

    public OidcDiscoveryDto setRequest_uri_parameter_supported(boolean request_uri_parameter_supported) {
        this.request_uri_parameter_supported = request_uri_parameter_supported;
        return this;
    }

    public boolean isRequire_request_uri_registration() {
        return require_request_uri_registration;
    }

    public OidcDiscoveryDto setRequire_request_uri_registration(boolean require_request_uri_registration) {
        this.require_request_uri_registration = require_request_uri_registration;
        return this;
    }

    public boolean isClaims_parameter_supported() {
        return claims_parameter_supported;
    }

    public OidcDiscoveryDto setClaims_parameter_supported(boolean claims_parameter_supported) {
        this.claims_parameter_supported = claims_parameter_supported;
        return this;
    }

    public List<String> getId_token_signing_alg_values_supported() {
        return id_token_signing_alg_values_supported;
    }

    public OidcDiscoveryDto setId_token_signing_alg_values_supported(List<String> id_token_signing_alg_values_supported) {
        this.id_token_signing_alg_values_supported = id_token_signing_alg_values_supported;
        return this;
    }

    public List<String> getSubject_types_supported() {
        return subject_types_supported;
    }

    public OidcDiscoveryDto setSubject_types_supported(List<String> subject_types_supported) {
        this.subject_types_supported = subject_types_supported;
        return this;
    }

    public List<String> getClaims_supported() {
        return claims_supported;
    }

    public OidcDiscoveryDto setClaims_supported(List<String> claims_supported) {
        this.claims_supported = claims_supported;
        return this;
    }
}
