package com.fujieid.jap.ids.provider;

import com.fujieid.jap.ids.BaseIdsTest;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.exception.InvalidClientException;
import com.fujieid.jap.ids.exception.InvalidCodeException;
import com.fujieid.jap.ids.exception.InvalidRedirectUriException;
import com.fujieid.jap.ids.exception.UnsupportedGrantTypeException;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.model.enums.ResponseType;
import com.fujieid.jap.ids.service.Oauth2Service;
import com.fujieid.jap.ids.service.Oauth2ServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class IdsTokenProviderTest extends BaseIdsTest {
    Oauth2Service oauth2Service = new Oauth2ServiceImpl();
    IdsTokenProvider idsTokenProvider = new IdsTokenProvider(oauth2Service);
    IdsRequestParam idsRequestParam = new IdsRequestParam();

    private void initParam() {
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getAllClientDetail().get(0);
        idsRequestParam.setGrantType(GrantType.AUTHORIZATION_CODE.getType())
            .setClientId(clientDetail.getClientId())
            .setClientSecret(clientDetail.getClientSecret())
            .setScope("openid")
            .setState("state123123213")
            .setGrantType(GrantType.AUTHORIZATION_CODE.getType())
            .setResponseType(ResponseType.CODE.getType())
            .setRedirectUri("https://justauth.plus")
            .setUsername("test")
            .setPassword("test");
    }

    @Test
    public void generateAuthorizationCodeResponse() {
        this.initParam();
        Assert.assertThrows(InvalidCodeException.class, () -> idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock));
    }

    @Test
    public void generateAuthorizationCodeResponseHasCache() {
        this.initParam();
        String code = oauth2Service.createAuthorizationCode(idsRequestParam, new UserInfo(), 100000L);
        idsRequestParam.setCode(code);
        IdsResponse<String, Object> response = idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock);
        System.out.println(response);
        Assert.assertNotNull(response);
    }

    @Test
    public void generateAuthorizationCodeResponseInvalidClient() {
        this.initParam();
        String code = oauth2Service.createAuthorizationCode(idsRequestParam, new UserInfo(), 100000L);
        idsRequestParam.setCode(code);
        idsRequestParam.setClientId("asdasd");
        Assert.assertThrows(InvalidClientException.class, () -> idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock));
    }

    @Test
    public void generateAuthorizationCodeResponseInvalidGrantType() {
        this.initParam();
        String code = oauth2Service.createAuthorizationCode(idsRequestParam, new UserInfo(), 100000L);
        idsRequestParam.setCode(code);
        idsRequestParam.setGrantType("asdasd");
        Assert.assertThrows(UnsupportedGrantTypeException.class, () -> idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock));
    }

    @Test
    public void generateAuthorizationCodeResponseInvalidRedirectUri() {
        this.initParam();
        String code = oauth2Service.createAuthorizationCode(idsRequestParam, new UserInfo(), 100000L);
        idsRequestParam.setCode(code);
        idsRequestParam.setRedirectUri("asdasd");
        Assert.assertThrows(InvalidRedirectUriException.class, () -> idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock));
    }

    @Test
    public void generateAuthorizationCodeResponseInvalidSecret() {
        this.initParam();
        String code = oauth2Service.createAuthorizationCode(idsRequestParam, new UserInfo(), 100000L);
        idsRequestParam.setCode(code);
        idsRequestParam.setClientSecret("asdasd");
        Assert.assertThrows(InvalidClientException.class, () -> idsTokenProvider.generateAuthorizationCodeResponse(idsRequestParam, httpServletRequestMock));
    }

    @Test
    public void generatePasswordResponse() {
        this.initParam();
    }

    @Test
    public void generateClientCredentialsResponse() {
        this.initParam();
    }

    @Test
    public void generateRefreshTokenResponse() {
        this.initParam();
    }
}
