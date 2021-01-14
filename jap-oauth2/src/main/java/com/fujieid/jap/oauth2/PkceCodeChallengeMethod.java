package com.fujieid.jap.oauth2;

/**
 * Encryption method of pkce challenge code
 * <p>
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/14 11:56
 * @see <a href="https://docs.fujieid.com/college/protocol/oauth-2.0/oauth-2.0-pkce" target="_blank">https://docs.fujieid.com/college/protocol/oauth-2.0/oauth-2.0-pkce</a>
 * @see <a href="https://tools.ietf.org/html/rfc7636" target="_blank">https://tools.ietf.org/html/rfc7636</a>
 * @since 1.0.0
 */
public enum PkceCodeChallengeMethod {

    /**
     * code_challenge = code_verifier
     */
    PLAIN,
    /**
     * code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
     */
    S256
}
