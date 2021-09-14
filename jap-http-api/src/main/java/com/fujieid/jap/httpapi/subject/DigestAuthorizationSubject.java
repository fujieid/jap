package com.fujieid.jap.httpapi.subject;

/**
 * The Authorization Request Header
 * @see <a href="https://en.wikipedia.org/wiki/Digest_access_authentication" target="_blank">https://en.wikipedia.org/wiki/Digest_access_authentication</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc2069#section-2.1.2" target="_blank">https://datatracker.ietf.org/doc/html/rfc2069#section-2.1.2</a>
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class DigestAuthorizationSubject{
    private String username;
    private String realm;
    private String nonce;
    private String uri;
    private String response;
    private String qop;
    private String nc;
    private String cnonce;
    private String pop;
    private String opaque;
    private String digest;
    private String algorithm;

    public String getQop() {
        return qop;
    }

    public DigestAuthorizationSubject setQop(String qop) {
        this.qop = qop;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DigestAuthorizationSubject setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRealm() {
        return realm;
    }

    public DigestAuthorizationSubject setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    public String getNonce() {
        return nonce;
    }

    public DigestAuthorizationSubject setNonce(String nonce) {
        this.nonce = nonce;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public DigestAuthorizationSubject setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getPop() {
        return pop;
    }

    public DigestAuthorizationSubject setPop(String pop) {
        this.pop = pop;
        return this;
    }

    public String getNc() {
        return nc;
    }

    public DigestAuthorizationSubject setNc(String nc) {
        this.nc = nc;
        return this;
    }

    public String getCnonce() {
        return cnonce;
    }

    public DigestAuthorizationSubject setCnonce(String cnonce) {
        this.cnonce = cnonce;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public DigestAuthorizationSubject setResponse(String response) {
        this.response = response;
        return this;
    }

    public String getOpaque() {
        return opaque;
    }

    public DigestAuthorizationSubject setOpaque(String opaque) {
        this.opaque = opaque;
        return this;
    }

    public String getDigest() {
        return digest;
    }

    public DigestAuthorizationSubject setDigest(String digest) {
        this.digest = digest;
        return this;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public DigestAuthorizationSubject setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }
}
