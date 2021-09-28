package com.fujieid.jap.ldap.password;

import org.apache.commons.codec.digest.UnixCrypt;
import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Random;

public class LdapPasswordUtilTest {

    @Test
    public void getAlgorithm() {
        String[] arr = new String[]{
            "{MD5}4QrcOUm6Wau+VuBX8g+IPg==",
            "{K5KEY}123456",
            "{SMD5}qx2PeUUZwWTldNSFJWav0+H2c68=",
            "{SHA}fEqNCco3Yq9h5ZUglD3CZJT4lBs=",
            "{SSHA}ZAMWfL5n96G2O5YlgCGGLfwp/+HOY9QA",
            "{SHA512}ujJTh2rta8ItSm/1PYQGxq2GQZXtFEq1yHYhtsIztUi66uaVbfNG7IwX9eoQ817jy8UUeX7X3dMUVGTioLq0Ew==",
            "{CRYPT}b785FaYHDFNCI",
            "{CRYPT}_uiCfk84UD.Kappl.tJU",
            "{CRYPT}$1$iUO9tzUE$7ixRoo26S/4ZoJywINpKo0",
            "{CRYPT}$5$YuXmJhhI$SylTrOuTBXAWC.PmJfaj5kGQtwkbUEsLwK.c8k0mpM2",
            "{CRYPT}$6$nNLKOfFc$XA9TM0MJtSrCCkZYTkaKlRYZ/mvLehnZ9ovX0WHaqPgMIuiMQxcgUKpp6ZVM2Kuqqk1e2UZUsKX4VJ0YAPmgx1",
        };
        for (String s : arr) {
            System.out.println("Start matching string: " + s);
            System.out.println(LdapPasswordUtil.getAlgorithm(s));
        }
    }

    @Test
    public void checkPassword() {
        LdapPasswordMatch ldapPasswordMatch = new LdapK5keyPasswordMatcher();
        boolean ok = ldapPasswordMatch.matches("123456", "{K5KEY}123456");
        Assert.assertTrue(ok);
    }

    @Test
    public void extDesc(){
        System.out.println(UnixCrypt.crypt("123456", ".tJU"));
        System.out.println(UnixCrypt.crypt("123456", "uiCfk84UD."));
        System.out.println(UnixCrypt.crypt("123456", B64.getRandomSalt(4)));
        for (int i = 5; i < 32; i++) {
            System.out.println(UnixCrypt.crypt("123456", B64.getRandomSalt(i)));
        }
    }
    static class B64 {
        static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        static String getRandomSalt(int num) {
            return getRandomSalt(num, new SecureRandom());
        }

        static String getRandomSalt(int num, Random random) {
            StringBuilder saltString = new StringBuilder(num);

            for (int i = 1; i <= num; ++i) {
                saltString.append(B64T.charAt(random.nextInt(B64T.length())));
            }

            return saltString.toString();
        }
    }

}
