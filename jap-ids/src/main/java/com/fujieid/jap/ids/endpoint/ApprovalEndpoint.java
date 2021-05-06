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
package com.fujieid.jap.ids.endpoint;

import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.IdsScope;
import com.fujieid.jap.ids.provider.IdsRequestParamProvider;
import com.fujieid.jap.ids.provider.IdsScopeProvider;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Confirm authorization endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class ApprovalEndpoint extends AbstractEndpoint {

    /**
     * The default authorization confirmation page pops up
     *
     * @param request  Current request
     * @param response Current response
     * @throws IOException IOException
     */
    public void showConfirmPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String approvalContent = createConfirmPageHtml(request);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().append(approvalContent);
    }

    /**
     * Obtain authorization information when you jump to the authorization confirmation page after successful login
     *
     * @param request HttpServletRequest
     * @return IdsResponse
     */
    public IdsResponse<String, Map<String, Object>> getAuthClientInfo(HttpServletRequest request) {
        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());
        OauthUtil.validClientDetail(clientDetail);

        List<Map<String, Object>> scopeInfo = getScopeInfo(param);

        Map<String, Object> result = new HashMap<>(5);
        result.put("appInfo", clientDetail);
        result.put("scopes", scopeInfo);
        result.put("params", param);

        return new IdsResponse<String, Map<String, Object>>().data(result);
    }

    /**
     * Generate the html of the authorization confirmation page
     *
     * @param request Current request
     * @return Confirm the html of the authorization page
     */
    private String createConfirmPageHtml(HttpServletRequest request) {
        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);
        String clientId = param.getClientId();
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(clientId);
        OauthUtil.validClientDetail(clientDetail);

        StringBuilder builder = new StringBuilder();
        String html = "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "  <head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
            + "    <meta name=\"description\" content=\"\">\n"
            + "    <meta name=\"author\" content=\"\">\n"
            + "    <title>OAuth Approval</title>\n"
            + "  </head>\n"
            + "  \n";
        builder.append(html).append("<body><h1>OAuth Approval</h1>");
        builder.append("<p>Do you authorize \"<strong>").append(clientDetail.getAppName()).append("</strong>");
        builder.append(" (").append(clientId).append(")");
        builder.append("\" to access your protected resources?</p>");
        builder.append("<form id=\"confirmationForm\" name=\"confirmationForm\" action=\"");

        String requestPath = ObjectUtils.appendIfNotEndWith(JapIds.getIdsConfig().getAuthorizeUrl(), "?") + request.getQueryString();
        builder.append(requestPath).append("\" method=\"post\">");
        builder.append("<input name=\"user_oauth_approval\" value=\"true\" type=\"hidden\"/>");

        String authorizeInputTemplate = "<label><input name=\"authorize\" value=\"Authorize\" type=\"submit\"/></label></form>";

        if (param.getScope() != null) {
            builder.append(createScopes(param, request));
            builder.append(authorizeInputTemplate);
        } else {
            builder.append(authorizeInputTemplate);
            builder.append("<form id=\"denialForm\" name=\"denialForm\" action=\"");
            builder.append(requestPath).append("/oauth/authorize\" method=\"post\">");
            builder.append("<input name=\"user_oauth_approval\" value=\"false\" type=\"hidden\"/>");
            builder.append("<label><input name=\"deny\" value=\"Deny\" type=\"submit\"/></label></form>");
        }

        builder.append("</body></html>");

        return builder.toString();
    }

    /**
     * Generate the scope list of the authorization confirmation page
     *
     * @param param   Parameters of the current request
     * @param request Current request
     * @return the scope list of the authorization confirmation page
     */
    private String createScopes(IdsRequestParam param, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("<ul style=\"list-style: none;padding-inline-start: 20px;\">");
        List<Map<String, Object>> scopeInfo = getScopeInfo(param);

        for (Map<String, Object> scope : scopeInfo) {
            String approved = (Boolean) scope.get("selected") ? " checked" : "";
            String denied = (Boolean) scope.get("selected") ? "" : " checked";

            builder.append("<li><div class=\"form-group\">");
            builder.append("<input type=\"checkbox\" name=\"scopes\"").append(" value=\"").append(scope.get("code")).append("\"").append(approved).append(" style=\"margin-right: 5px;\">")
                .append(scope.get("code")).append(" - ").append(scope.get("description"))
                .append("</input> ");
            builder.append(denied).append("</div></li>");
        }
        builder.append("</ul>");
        return builder.toString();
    }

    /**
     * Reorganize scope information
     *
     * @param param Parameters of the current request
     * @return List
     */
    private List<Map<String, Object>> getScopeInfo(IdsRequestParam param) {
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());

        Set<String> userAuthorizedScopes = OauthUtil.validateScope(param.getScope(), clientDetail.getScopes());

        Set<String> supportedScopes = OauthUtil.convertStrToList(clientDetail.getScopes());

        List<IdsScope> scopeList = IdsScopeProvider.getScopeByCodes(supportedScopes);

        List<Map<String, Object>> scopeInfo = new LinkedList<>();
        Map<String, Object> scopeItem = null;
        for (IdsScope idsScope : scopeList) {
            scopeItem = new HashMap<>(5);
            scopeItem.put("code", idsScope.getCode());
            scopeItem.put("description", idsScope.getDescription());
            scopeItem.put("selected", userAuthorizedScopes.contains(idsScope.getCode()));
            scopeInfo.add(scopeItem);
        }
        return scopeInfo;
    }
}
