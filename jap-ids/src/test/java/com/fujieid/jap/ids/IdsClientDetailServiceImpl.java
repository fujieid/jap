package com.fujieid.jap.ids;

import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.enums.GrantType;
import com.fujieid.jap.ids.model.enums.ResponseType;
import com.fujieid.jap.ids.provider.IdsScopeProvider;
import com.fujieid.jap.ids.service.IdsClientDetailService;
import com.fujieid.jap.ids.util.OauthUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class IdsClientDetailServiceImpl implements IdsClientDetailService {
    public List<ClientDetail> clientDetails = new LinkedList<>();

    public IdsClientDetailServiceImpl() {

        clientDetails.add(new ClientDetail()
            .setId("2")
            .setAppName("授权码授权模式")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes("authorization_code")
            .setResponseTypes(ResponseType.CODE.getType())
            .setAvailable(true)
        );
        clientDetails.add(new ClientDetail()
            .setId("3")
            .setAppName("隐式授权模式")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes(GrantType.IMPLICIT.getType())
            .setResponseTypes(ResponseType.TOKEN.getType())
            .setAvailable(true)
        );
        clientDetails.add(new ClientDetail()
            .setId("4")
            .setAppName("密码授权模式")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes(GrantType.PASSWORD.getType())
            .setAvailable(true)
        );
        clientDetails.add(new ClientDetail()
            .setId("5")
            .setAppName("客户端授权模式")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes(GrantType.CLIENT_CREDENTIALS.getType())
            .setAvailable(true)
        );
        clientDetails.add(new ClientDetail()
            .setId("6")
            .setAppName("被禁用的客户端")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes(GrantType.CLIENT_CREDENTIALS.getType())
            .setAvailable(false)
        );
        clientDetails.add(new ClientDetail()
            .setId("1")
            .setAppName("适应所有场景")
            .setClientId(OauthUtil.generateClientId())
            .setClientSecret(OauthUtil.generateClientSecret())
            .setRedirectUri("https://justauth.plus")
            .setScopes(String.join(" ", IdsScopeProvider.getScopeCodes()))
            .setGrantTypes(String.join(" ", GrantType.grantTypes()))
            .setResponseTypes(String.join(" ", Arrays.asList(ResponseType.CODE.getType(), ResponseType.TOKEN.getType(), ResponseType.ID_TOKEN.getType(), ResponseType.NONE.getType())))
            .setAvailable(true)
        );
        clientDetails.forEach(clientDetail -> {
            System.out.println("client detail: " + clientDetail.getAppName() + ", " + clientDetail.getClientId());
        });
    }

    /**
     * 通过 client_id 查询客户端信息
     *
     * @param clientId 客户端应用id
     * @return AppOauthClientDetails
     */
    @Override
    public ClientDetail getByClientId(String clientId) {
        return clientDetails.stream().filter(client -> client.getClientId().equals(clientId)).findFirst().orElse(null);
    }

    /**
     * Add client
     *
     * @param clientDetail Client application details
     * @return ClientDetail
     */
    @Override
    public ClientDetail add(ClientDetail clientDetail) {
        return IdsClientDetailService.super.add(clientDetail);
    }

    /**
     * Modify the client
     *
     * @param clientDetail Client application details
     * @return ClientDetail
     */
    @Override
    public ClientDetail update(ClientDetail clientDetail) {
        return IdsClientDetailService.super.update(clientDetail);
    }

    /**
     * Delete client by primary key
     *
     * @param id Primary key of the client application
     * @return boolean
     */
    @Override
    public boolean removeById(String id) {
        return IdsClientDetailService.super.removeById(id);
    }

    /**
     * Delete client by client id
     *
     * @param clientId Client application id
     * @return ClientDetail
     */
    @Override
    public boolean removeByClientId(String clientId) {
        return IdsClientDetailService.super.removeByClientId(clientId);
    }

    /**
     * 获取所有 client detail
     *
     * @return List
     */
    @Override
    public List<ClientDetail> getAllClientDetail() {
        return clientDetails;
    }
}
