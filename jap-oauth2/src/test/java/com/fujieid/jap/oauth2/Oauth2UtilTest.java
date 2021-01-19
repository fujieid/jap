package com.fujieid.jap.oauth2;

import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;
import org.junit.Assert;
import org.junit.Test;

public class Oauth2UtilTest {

    @Test
    public void generateCodeChallenge() {
        String challenge = Oauth2Util.generateCodeChallenge(PkceCodeChallengeMethod.S256, "asdasdasdasd");
        Assert.assertEquals(challenge, "ZrETKgFzkQsB7joV705pWDu_L38eRGLJnvvhuatb-Ag");
    }

    @Test
    public void generateCodeVerifier() {
        String verifier = Oauth2Util.generateCodeVerifier();
        System.out.println(verifier);
    }
}
