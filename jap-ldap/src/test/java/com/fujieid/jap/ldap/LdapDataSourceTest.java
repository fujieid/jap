package com.fujieid.jap.ldap;

import org.junit.Test;

import javax.naming.directory.DirContext;

public class LdapDataSourceTest {

    LdapConfig ldapConfig = new LdapConfig()
        .setUrl("ldap://localhost:389")
        .setBindDn("cn=admin,dc=test,dc=com")
        .setCredentials("123456")
        .setBaseDn("dc=test,dc=com")
        .setFilters("")
        .setTrustStore("")
        .setTrustStorePassword("");

    @Test
    public void openConnection() {
        LdapDataSource ldapDataSource = new LdapDataSource(ldapConfig);
        DirContext dirContext = ldapDataSource.openConnection();
        System.out.println(dirContext);
    }

    @Test
    public void authenticate() {
        LdapDataSource ldapDataSource = new LdapDataSource(ldapConfig);
        boolean res = ldapDataSource.authenticate();
        System.out.println(res);
    }

    @Test
    public void getConnection() {
        LdapDataSource ldapDataSource = new LdapDataSource(ldapConfig);
        DirContext dirContext = ldapDataSource.getConnection();
        System.out.println(dirContext);
    }
}
