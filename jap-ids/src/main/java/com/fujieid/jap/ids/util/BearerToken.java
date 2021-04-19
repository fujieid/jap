/*
 * Copyright (C) 2014 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.ids.util;


import com.fujieid.jap.ids.model.IdsConsts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class for Bearer Token defined in
 * <a href="http://tools.ietf.org/html/rfc6750">RFC 6750</a>.
 *
 * @author Takahiko Kawasaki
 * @see <a href="http://tools.ietf.org/html/rfc6750">RFC 6750 (OAuth 2.0 Bearer Token Usage)</a>
 */
public class BearerToken {
    /**
     * Regular expression to parse {@code Authorization} header.
     */
    private static final Pattern CHALLENGE_PATTERN
        = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);


    private BearerToken() {
    }


    /**
     * Extract the access token embedded in the input string.
     *
     * <p>
     * This method assumes that the input string comes from one of
     * the following three places that are mentioned in "RFC 6750
     * (OAuth 2.0 Bearer Token Usage),
     * <a href="http://tools.ietf.org/html/rfc6750#section-2"
     * >2. Authenticated Requests</a>".
     * </p>
     *
     * <blockquote>
     * <ol>
     *   <li><a href="http://tools.ietf.org/html/rfc6750#section-2.1"
     *       >Authorization Request Header Field</a>
     *   <li><a href="http://tools.ietf.org/html/rfc6750#section-2.2"
     *       >Form-Encoded Body Parameter</a>
     *   <li><a href="http://tools.ietf.org/html/rfc6750#section-2.3"
     *       >URI Query Parameter</a>
     * </ol>
     * </blockquote>
     *
     * <p>
     * To be concrete, this method assumes that the format of the
     * input string is either of the following two.
     * </p>
     *
     * <blockquote>
     * <ol>
     *   <li><code>"Bearer <i>{access-token}</i>"</code>
     *   <li>Parameters formatted in <code>application/x-www-form-urlencoded</code>
     *       containing <code>access_token=<i>{access-token}</i></code>.
     * </ol>
     * </blockquote>
     *
     * <p>
     * For example, both {@link BearerToken#parse(String) parse} method calls below
     * return <code>"hello-world"</code>.
     * </p>
     *
     * <pre>
     * BearerToken.parse("Bearer hello-world");
     * BearerToken.parse("key1=value1&amp;access_token=hello-world");
     * </pre>
     *
     * @param input The input string to be parsed.
     * @return The extracted access token, or <code>null</code> if not found.
     * @see <a href="http://tools.ietf.org/html/rfc6750"
     * >RFC 6750 (OAuth 2.0 Bearer Token Usage)</a>
     */
    public static String parse(String input) {
        if (input == null) {
            return null;
        }

        if (!input.startsWith(IdsConsts.TOKEN_TYPE_BEARER) && !input.contains("&")) {
            return input;
        }

        // First, check whether the input matches the pattern
        // "Bearer {access-token}".
        Matcher matcher = CHALLENGE_PATTERN.matcher(input);

        // If the input matches the pattern.
        if (matcher.matches()) {
            // Return the value as is. Note that it is not Base64-encoded.
            // See https://www.ietf.org/mail-archive/web/oauth/current/msg08489.html
            return matcher.group(1);
        } else {
            // Assume that the input is formatted in
            // application/x-www-form-urlencoded.
            return extractFromFormParameters(input);
        }
    }


    private static String extractFromFormParameters(String input) {
        for (String parameter : input.split("&")) {
            String[] pair = parameter.split("=", 2);

            if (pair.length != 2 || pair[1].length() == 0) {
                continue;
            }

            if (!"access_token".equals(pair[0])) {
                continue;
            }

            try {
                // URL-decode
                return URLDecoder.decode(pair[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // This won't happen.
                return null;
            }
        }

        // Not found.
        return null;
    }
}
