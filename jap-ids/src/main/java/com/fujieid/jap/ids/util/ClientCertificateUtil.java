/*
 * Copyright (c) 2020-2040, 北京符节科技有限公司 (support@fujieid.com & https://www.fujieid.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fujieid.jap.ids.util;

import cn.hutool.core.util.ObjectUtil;
import com.fujieid.jap.http.JapHttpRequest;
import com.fujieid.jap.http.RequestUtil;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.model.ClientCertificate;
import com.fujieid.jap.ids.model.IdsConsts;
import com.fujieid.jap.ids.model.enums.ClientSecretAuthMethod;
import com.xkcoding.json.util.StringUtil;

import java.util.Collections;
import java.util.List;

/**
 * Get the client secret, client id from the request
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClientCertificateUtil {

    public static ClientCertificate getClientCertificate(JapHttpRequest request) {
        List<ClientSecretAuthMethod> clientSecretAuthMethods = JapIds.getIdsConfig().getClientSecretAuthMethods();
        if (ObjectUtil.isEmpty(clientSecretAuthMethods)) {
            clientSecretAuthMethods = Collections.singletonList(ClientSecretAuthMethod.ALL);
        }

        if (clientSecretAuthMethods.contains(ClientSecretAuthMethod.ALL)) {
            ClientCertificate clientCertificate = getClientCertificateFromRequestParameter(request);
            if (StringUtil.isEmpty(clientCertificate.getId())) {
                clientCertificate = getClientCertificateFromHeader(request);
            }
            return clientCertificate;

        } else {

            if (clientSecretAuthMethods.contains(ClientSecretAuthMethod.CLIENT_SECRET_POST)
                || clientSecretAuthMethods.contains(ClientSecretAuthMethod.NONE)) {
                return getClientCertificateFromRequestParameter(request);
            }

            if (clientSecretAuthMethods.contains(ClientSecretAuthMethod.CLIENT_SECRET_BASIC)) {
                return getClientCertificateFromHeader(request);
            }
        }
        return new ClientCertificate();
    }

    private static ClientCertificate getClientCertificateFromRequestParameter(JapHttpRequest request) {
        String clientId = RequestUtil.getParam(IdsConsts.CLIENT_ID, request);
        String clientSecret = RequestUtil.getParam(IdsConsts.CLIENT_SECRET, request);
        return new ClientCertificate(clientId, clientSecret);
    }

    private static ClientCertificate getClientCertificateFromHeader(JapHttpRequest request) {
        String authorizationHeader = RequestUtil.getHeader(IdsConsts.AUTHORIZATION_HEADER_NAME, request);
        if (StringUtil.isNotEmpty(authorizationHeader)) {
            BasicCredentials credentials = BasicCredentials.parse(authorizationHeader);
            return credentials.getClientCertificate();
        }
        return new ClientCertificate();
    }

}
