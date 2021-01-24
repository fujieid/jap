package com.fujieid.jap.simple;

/**
 * @author harrylee (harryleexyz(a)qq.com)
 * @version 1.0.0
 * @date 2021/1/24 18:57
 * @since 1.0.0
 */
public class RememberMeDetails {

    /**
     * username
     */
    private String username;

    /**
     * expiry time
     */
    private long expiryTime;
    /**
     * encode value
     */
    private String encodeValue;

    public String getUsername() {
        return username;
    }

    public RememberMeDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public RememberMeDetails setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    public String getEncodeValue() {
        return encodeValue;
    }

    public RememberMeDetails setEncodeValue(String encodeValue) {
        this.encodeValue = encodeValue;
        return this;
    }
}
