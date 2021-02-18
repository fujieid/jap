package com.fujieid.jap.oauth2;

import com.fujieid.jap.core.cache.JapCache;
import com.fujieid.jap.core.cache.JapCacheContextHolder;
import com.fujieid.jap.core.cache.JapLocalCache;
import com.fujieid.jap.core.exception.JapOauth2Exception;
import com.fujieid.jap.oauth2.pkce.PkceCodeChallengeMethod;
import com.xkcoding.json.util.Kv;
import org.junit.Assert;
import org.junit.Test;

public class Oauth2UtilTest {

    @Test
    public void generateCodeChallengeCodeChallengeMethodIsS256() {
        String s256Challenge = Oauth2Util.generateCodeChallenge(PkceCodeChallengeMethod.S256, "asdasdasdasd");
        Assert.assertEquals("ZrETKgFzkQsB7joV705pWDu_L38eRGLJnvvhuatb-Ag", s256Challenge);
    }

    @Test
    public void generateCodeChallengeCodeChallengeMethodIsPlain() {
        String plainChallenge = Oauth2Util.generateCodeChallenge(PkceCodeChallengeMethod.PLAIN, "asdasdasdasd");
        Assert.assertEquals("asdasdasdasd", plainChallenge);
    }

    @Test
    public void generateCodeVerifier() {
        String verifier = Oauth2Util.generateCodeVerifier();
        System.out.println(verifier);
        Assert.assertEquals(67, verifier.length());
    }

    @Test
    public void checkOauthResponseNoError() {
        Kv kv = new Kv();
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg);
    }

    @Test
    public void checkOauthResponseHasEmptyError() {
        Kv kv = new Kv();
        kv.put("error", "");
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg));
    }

    @Test
    public void checkOauthResponseHasNullError() {
        Kv kv = new Kv();
        kv.put("error", null);
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg));
    }

    @Test
    public void checkOauthResponseHasError() {
        Kv kv = new Kv();
        kv.put("error", "invalid_request");
        kv.put("error_description", "invalid_request_description");
        String responseStr = "responseStr";
        String errorMsg = "errorMsg";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthResponse(responseStr, kv, errorMsg));
    }

    @Test
    public void checkOauthCallbackRequestNullError() {
        Oauth2Util.checkOauthCallbackRequest(null, null, null);
    }

    @Test
    public void checkOauthCallbackRequestEmptyError() {
        Oauth2Util.checkOauthCallbackRequest("", null, null);
    }

    @Test
    public void checkOauthCallbackRequestHasError() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = "bizErrorMsg";
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkOauthCallbackRequestHasErrorAndEmptyBizErrorMsg() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = "";
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkOauthCallbackRequestHasErrorAndNullBizErrorMsg() {
        String requestErrorDescParam = "requestErrorDescParam";
        String bizErrorMsg = null;
        String requestErrorParam = "requestErrorParam";
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkOauthCallbackRequest(requestErrorParam, requestErrorDescParam, bizErrorMsg));
    }

    @Test
    public void checkStateNoVerify() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = false;
        Oauth2Util.checkState(state, clientId, verifyState);
    }

    @Test
    public void checkStateCacheExists() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = true;
        JapCache cache = new JapLocalCache();
        cache.set(Oauth2Const.STATE_CACHE_KEY.concat(clientId), state);
        JapCacheContextHolder.enable(cache);
        Oauth2Util.checkState(state, clientId, verifyState);
    }

    @Test
    public void checkStateNoCache() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = true;
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkState(state, clientId, verifyState));
    }

    @Test
    public void checkStateCacheDoesNotExist() {
        String state = "xxx";
        String clientId = "xx";
        boolean verifyState = true;
        JapCache cache = new JapLocalCache();
        cache.set(Oauth2Const.STATE_CACHE_KEY.concat(clientId), "11");
        JapCacheContextHolder.enable(cache);
        Assert.assertThrows(JapOauth2Exception.class, () -> Oauth2Util.checkState(state, clientId, verifyState));
    }


}
