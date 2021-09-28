package com.fujieid.jap.ldap;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.6
 */
public class LdapDataSource {
    private static final Log log = LogFactory.get();
    private static final String REFERRAL = "ignore";
    private final LdapConfig ldapConfig;
    private final boolean ssl;
    private DirContext ctx;

    public LdapDataSource(LdapConfig ldapConfig) {
        this.ldapConfig = ldapConfig;
        this.ssl = ldapConfig.getUrl().toLowerCase().startsWith("ldaps");
    }

    public DirContext openConnection() {
        return initialDirContext(getEnvironment());
    }

    private Hashtable<String, String> getEnvironment() {
        Hashtable<String, String> environment = new Hashtable<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
        environment.put(Context.REFERRAL, REFERRAL);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");

        environment.put(Context.PROVIDER_URL, ldapConfig.getUrl());
        environment.put(Context.SECURITY_PRINCIPAL, ldapConfig.getBindDn());
        environment.put(Context.SECURITY_CREDENTIALS, ldapConfig.getCredentials());

        if (ssl) {
            System.setProperty("javax.net.ssl.trustStore", ldapConfig.getTrustStore());
            System.setProperty("javax.net.ssl.trustStorePassword", ldapConfig.getTrustStorePassword());
            environment.put(Context.SECURITY_PROTOCOL, "ssl");
            environment.put(Context.REFERRAL, "follow");
        }
        return environment;
    }

    protected DirContext initialDirContext(Hashtable<?, ?> environment) {
        try {
            ctx = new InitialDirContext(environment);
            log.info("connect to ldap " + ldapConfig.getUrl() + " successful.");
        } catch (NamingException e) {
            log.error("connect to ldap " + ldapConfig.getUrl() + " failure.", e);
        }
        return ctx;
    }

    public boolean authenticate() {
        DirContext ctx = openConnection();
        if (ctx != null) {
            close();
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        if (null != this.ctx) {
            try {
                this.ctx.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            } finally {
                this.ctx = null;
            }
        }
    }

    public DirContext getConnection() {
        if (ctx == null) {
            openConnection();
        }
        return ctx;
    }

    public LdapConfig getLdapConfig() {
        return ldapConfig;
    }
}
