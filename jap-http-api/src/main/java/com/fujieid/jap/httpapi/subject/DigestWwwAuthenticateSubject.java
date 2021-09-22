package com.fujieid.jap.httpapi.subject;

/**
 * The WWW-Authenticate Response Header
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc2069#section-2.1.1" target="_blank">https://datatracker.ietf.org/doc/html/rfc2069#section-2.1.1</a>
 * @since 1.0.5
 */
public class DigestWwwAuthenticateSubject {
    private String realm;
    private String domain;
    private String nonce;
    private String opaque;
    private String algorithm;
    private String stale;
    private String qop;

    public DigestWwwAuthenticateSubject() {
    }

    public String getQop() {
        return qop;
    }

    public DigestWwwAuthenticateSubject setQop(String qop) {
        this.qop = qop;
        return this;
    }

    public String getRealm() {
        return realm;
    }

    public DigestWwwAuthenticateSubject setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public DigestWwwAuthenticateSubject setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public DigestWwwAuthenticateSubject setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getOpaque() {
        return opaque;
    }

    public DigestWwwAuthenticateSubject setOpaque(String opaque) {
        this.opaque = opaque;
        return this;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public DigestWwwAuthenticateSubject setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public String getStale() {
        return stale;
    }

    public DigestWwwAuthenticateSubject setStale(String stale) {
        this.stale = stale;
        return this;
    }
}
