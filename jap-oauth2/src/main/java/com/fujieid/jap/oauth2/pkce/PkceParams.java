package com.fujieid.jap.oauth2.pkce;

/**
 * OAuth PKCE Parameters Registry
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/18 16:49
 * @see <a href="https://tools.ietf.org/html/rfc7636#section-6.1" target="_blank">6.1.  OAuth Parameters Registry</a>
 * @since 1.0.0
 */
public interface PkceParams {

    /**
     * {@code code_challenge} - used in Authorization Request.
     */
    String CODE_CHALLENGE = "code_challenge";

    /**
     * {@code code_challenge_method} - used in Authorization Request.
     */
    String CODE_CHALLENGE_METHOD = "code_challenge_method";

    /**
     * {@code code_verifier} - used in Token Request.
     */
    String CODE_VERIFIER = "code_verifier";

}
