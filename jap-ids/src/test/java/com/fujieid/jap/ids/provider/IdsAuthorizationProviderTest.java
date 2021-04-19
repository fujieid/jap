package com.fujieid.jap.ids.provider;

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.model.enums.ResponseType;
import com.fujieid.jap.ids.service.Oauth2ServiceImpl;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.ObjectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IdsAuthorizationProviderTest extends BaseIdsTest {
    IdsAuthorizationProvider idsAuthorizationProvider = new IdsAuthorizationProvider(new Oauth2ServiceImpl());
    UserInfo userInfo = new UserInfo();
    IdsRequestParam idsRequestParam = new IdsRequestParam();
    ClientDetail clientDetail = new ClientDetail();

    public IdsAuthorizationProviderTest() {
        userInfo.setId("id11111")
            .setUsername("testUsername");

        idsRequestParam.setGrantType(GrantType.AUTHORIZATION_CODE.getType())
            .setScope("openid")
            .setState("state123123213")
            .setRedirectUri("https://justauth.plus");

        clientDetail
            .setId("2")
            .setAppName("授权码授权模式")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("http://localhost:8080")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes("authorization_code")
            .setResponseTypes(ResponseType.CODE.getType())
            .setAvailable(true);
    }

    @Test
    public void generateAuthorizationCodeResponse() {
        String url = idsAuthorizationProvider.generateAuthorizationCodeResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(2, obj.size());
        Assert.assertTrue(obj.containsKey("code"));
        Assert.assertTrue(obj.containsKey("state"));
    }

    @Test
    public void generateImplicitGrantResponse() {
        String url = idsAuthorizationProvider.generateImplicitGrantResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(6, obj.size());
        Assert.assertTrue(obj.containsKey("access_token"));
        Assert.assertTrue(obj.containsKey("scope"));
        Assert.assertTrue(obj.containsKey("id_token"));
        Assert.assertTrue(obj.containsKey("state"));
        Assert.assertTrue(obj.containsKey("token_type"));
        Assert.assertTrue(obj.containsKey("expires_in"));
    }

    @Test
    public void generateCodeIdTokenAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateCodeIdTokenAuthorizationResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(3, obj.size());
        Assert.assertTrue(obj.containsKey("code"));
        Assert.assertTrue(obj.containsKey("id_token"));
        Assert.assertTrue(obj.containsKey("state"));
    }

    @Test
    public void generateIdTokenAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateIdTokenAuthorizationResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(1, obj.size());
        Assert.assertTrue(obj.containsKey("id_token"));
    }

    @Test
    public void generateIdTokenTokenAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateIdTokenTokenAuthorizationResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(2, obj.size());
        Assert.assertTrue(obj.containsKey("access_token"));
        Assert.assertTrue(obj.containsKey("id_token"));
    }

    @Test
    public void generateCodeTokenAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateCodeTokenAuthorizationResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(3, obj.size());
        Assert.assertTrue(obj.containsKey("access_token"));
        Assert.assertTrue(obj.containsKey("code"));
        Assert.assertTrue(obj.containsKey("state"));
    }

    @Test
    public void generateCodeIdTokenTokenAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateCodeIdTokenTokenAuthorizationResponse(userInfo, idsRequestParam, clientDetail);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(4, obj.size());
        Assert.assertTrue(obj.containsKey("code"));
        Assert.assertTrue(obj.containsKey("state"));
        Assert.assertTrue(obj.containsKey("access_token"));
        Assert.assertTrue(obj.containsKey("id_token"));
    }

    @Test
    public void generateNoneAuthorizationResponse() {
        String url = idsAuthorizationProvider.generateNoneAuthorizationResponse(idsRequestParam);
        System.out.println(url);
        Assert.assertNotNull(url);
        String params = url.substring(idsRequestParam.getRedirectUri().length() + 1);
        Map<String, String> obj = ObjectUtils.parseStringToMap(params);
        Assert.assertEquals(1, obj.size());
        Assert.assertTrue(obj.containsKey("state"));
    }
}
