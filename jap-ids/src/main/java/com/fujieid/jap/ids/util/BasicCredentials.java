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
package com.fujieid.jap.ids.util;

import com.fujieid.jap.ids.model.ClientCertificate;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Credentials in Basic authentication.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @see <a href="http://tools.ietf.org/html/rfc2617#section-2">RFC 2617 (HTTP Authentication), 2. Basic Authentication Scheme</a>
 */
public class BasicCredentials {
    /**
     * Regular expression to parse {@code Authorization} header.
     */
    private static final Pattern CHALLENGE_PATTERN = Pattern.compile("^Basic *([^ ]+) *$", Pattern.CASE_INSENSITIVE);
    private final ClientCertificate clientCertificate;

    /**
     * "Basic {base64-encoded id:secret}"
     */
    private transient String formatted;


    /**
     * Constructor with credentials.
     *
     * @param id     The client ID.
     * @param secret The client secret.
     */
    public BasicCredentials(String id, String secret) {
        this.clientCertificate = new ClientCertificate(null == id ? "" : id, null == secret ? "" : secret);
    }

    /**
     * Parse {@code Authorization} header for Basic authentication.
     *
     * @param input The value of {@code Authorization} header. Expected inputs
     *              are either <code>"Basic <i>{Base64-Encoded-id-and-secret}</i>"</code>,
     *              or <code>"<i>{Base64-Encoded-id-and-secret}</i>"</code>.
     * @return Parsed credentials. If {@code input} is {@code null} is returned.
     */
    public static BasicCredentials parse(String input) {
        if (input == null) {
            return null;
        }

        Matcher matcher = CHALLENGE_PATTERN.matcher(input);

        if (!matcher.matches()) {
            return new BasicCredentials(null, null);
        }

        String encoded = matcher.group(1);
        byte[] decoded = Base64.decodeBase64(encoded);
        String value = new String(decoded, StandardCharsets.UTF_8);
        String[] credentials = value.split(":", 2);

        String id = credentials[0];
        String secret = null;
        if (credentials.length == 2) {
            secret = credentials[1];
        }

        return new BasicCredentials(id, secret);
    }

    public String getId() {
        return clientCertificate.getId();
    }

    public String getSecret() {
        return clientCertificate.getSecret();
    }

    public ClientCertificate getClientCertificate() {
        return clientCertificate;
    }

    /**
     * Create a value suitable as the value of {@code Authorization} header.
     *
     * @return {@code Authorization} header value for Basic authentication.
     */
    public String create() {
        if (formatted != null) {
            return formatted;
        }

        String credentials = String.format("%s:%s", clientCertificate.getId(), clientCertificate.getSecret());

        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);

        String encoded = Base64.encodeBase64String(credentialsBytes);

        return (formatted = "Basic " + encoded);
    }
}
