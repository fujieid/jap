package com.fujieid.jap.ldap.template;

import com.fujieid.jap.ldap.LdapConfig;
import com.fujieid.jap.ldap.LdapDataSource;
import com.fujieid.jap.ldap.model.LdapPerson;
import org.junit.Test;

public class LdapDefaultTemplateTest {

    @Test
    public void findPerson() {
        LdapTemplate ldapTemplate = new LdapDefaultTemplate(new LdapDataSource(new LdapConfig()
            .setUrl("ldap://localhost:389")
            .setBindDn("cn=admin,dc=test,dc=com")
            .setCredentials("123456")
            .setBaseDn("dc=test,dc=com")
            .setFilters("(&(objectClass=inetOrgPerson)(uid=%s))")
            .setTrustStore("")
            .setTrustStorePassword("")));
        LdapPerson person = ldapTemplate.findPerson("100012");
        System.out.println(person);
    }

    @Test
    public void login() {
        LdapTemplate ldapTemplate = new LdapDefaultTemplate(new LdapDataSource(new LdapConfig()
            .setUrl("ldap://localhost:389")
            .setBindDn("cn=admin,dc=test,dc=com")
            .setCredentials("123456")
            .setBaseDn("dc=test,dc=com")
            .setFilters("(&(objectClass=inetOrgPerson)(uid=%s))")
            .setTrustStore("")
            .setTrustStorePassword("")));
        // 测试通过
        // clear
        System.out.println(ldapTemplate.login("100012", "123456"));
        // md5
        System.out.println(ldapTemplate.login("100000", "123456"));
        // sha
        System.out.println(ldapTemplate.login("100002", "123456"));
        // ssha
        System.out.println(ldapTemplate.login("100006", "123456"));
        // sha512
        System.out.println(ldapTemplate.login("100003", "123456"));
        // k5key
        System.out.println(ldapTemplate.login("100009", "123456"));
        // md5crypt
        System.out.println(ldapTemplate.login("100008", "123456"));
        // sha256crypt
        System.out.println(ldapTemplate.login("100004", "123456"));
        // sha512crypt
        System.out.println(ldapTemplate.login("100005", "123456"));


        // 暂未测试通过
        // ext_des
        System.out.println(ldapTemplate.login("100010", "123456"));
        // smd5
        System.out.println(ldapTemplate.login("100007", "123456"));
        // crypt
        System.out.println(ldapTemplate.login("100001", "123456"));

    }
}
