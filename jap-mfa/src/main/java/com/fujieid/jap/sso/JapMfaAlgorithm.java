package com.fujieid.jap.sso;

/**
 * The cryptographic hash function used to calculate the HMAC (Hash-based Message Authentication Code).
 * <p>
 * This implementation uses the SHA1 hash function by default.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public enum JapMfaAlgorithm {
    /**
     * SHA1
     */
    HmacSHA1,
    /**
     * SHA256
     */
    HmacSHA256,
    /**
     * SHA512
     */
    HmacSHA512
}
