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

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fujieid.jap.ids.JapIds;
import com.fujieid.jap.ids.config.IdsConfig;
import com.fujieid.jap.ids.exception.IdsException;
import com.fujieid.jap.ids.model.ClientDetail;
import com.fujieid.jap.ids.model.IdsRequestParam;
import com.fujieid.jap.ids.model.IdsResponse;
import com.fujieid.jap.ids.model.UserInfo;
import com.fujieid.jap.ids.model.enums.ErrorResponse;
import com.fujieid.jap.ids.provider.IdsRequestParamProvider;
import com.fujieid.jap.ids.util.OauthUtil;
import com.fujieid.jap.ids.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Login Endpoint
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoginEndpoint extends AbstractEndpoint {

    /**
     * 显示默认的登录页面
     *
     * @param request  current request
     * @param response current response
     * @throws IOException IOException
     */
    public void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String loginPageHtml = generateLoginPageHtml(request);
        response.setContentType("text/html;charset=UTF-8");
        response.setContentLength(loginPageHtml.getBytes(StandardCharsets.UTF_8).length);
        response.getWriter().write(loginPageHtml);
    }

    private String generateLoginPageHtml(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "  <head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
            + "    <meta name=\"description\" content=\"\">\n"
            + "    <meta name=\"author\" content=\"\">\n"
            + "    <title>Please sign in</title>\n"
            + "    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n"
            + "    <link href=\"https://getbootstrap.com/docs/4.0/examples/signin/signin.css\" rel=\"stylesheet\" crossorigin=\"anonymous\"/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "     <div class=\"container\">\n");

        String authenticationUrl = ObjectUtils.appendIfNotEndWith(JapIds.getIdsConfig().getLoginUrl(), "?") + request.getQueryString();
        sb.append("      <form class=\"form-signin\" method=\"post\" action=\"").append(authenticationUrl).append("\">\n")
            .append("        <h2 class=\"form-signin-heading\">Please sign in</h2>\n")
            .append("        <p>\n")
            .append("          <label for=\"username\" class=\"sr-only\">Username</label>\n")
            .append("          <input type=\"text\" id=\"username\" name=\"").append(JapIds.getIdsConfig().getUsernameField())
            .append("\" class=\"form-control\" placeholder=\"Username\" required autofocus>\n").append("        </p>\n")
            .append("        <p>\n").append("          <label for=\"password\" class=\"sr-only\">Password</label>\n")
            .append("          <input type=\"password\" id=\"password\" name=\"")
            .append(JapIds.getIdsConfig().getPasswordField()).append("\" class=\"form-control\" placeholder=\"Password\" required>\n")
            .append("        </p>\n").append("        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Sign in</button>\n")
            .append("      </form>\n");

        sb.append("</div>\n");
        sb.append("</body></html>");

        return sb.toString();
    }

    /**
     * Login with account password
     *
     * @param request current request
     * @return Confirm authorization page
     */
    public IdsResponse<String, Object> signin(HttpServletRequest request) {
        IdsConfig idsConfig = JapIds.getIdsConfig();
        String username = request.getParameter(idsConfig.getUsernameField());
        String password = request.getParameter(idsConfig.getPasswordField());
        if (ObjectUtil.hasEmpty(username, password)) {
            throw new IdsException(ErrorResponse.INVALID_USER_CERTIFICATE);
        }
        UserInfo userInfo = JapIds.getContext().getUserService().loginByUsernameAndPassword(username, password);
        if (null == userInfo) {
            throw new IdsException(ErrorResponse.INVALID_USER_CERTIFICATE);
        }
        JapIds.saveUserInfo(userInfo, request);

        IdsRequestParam param = IdsRequestParamProvider.parseRequest(request);
        ClientDetail clientDetail = JapIds.getContext().getClientDetailService().getByClientId(param.getClientId());
        OauthUtil.validClientDetail(clientDetail);

        String redirectUri = null;
        // When the client supports automatic authorization, it will judge whether the {@code autoapprove} function is enabled
        if (null != clientDetail.getAutoApprove() && clientDetail.getAutoApprove() &&
            StrUtil.isNotEmpty(param.getAutoapprove()) && "TRUE".equalsIgnoreCase(param.getAutoapprove())) {
            redirectUri = JapIds.getIdsConfig().getAuthorizeAutoApproveUrl();
        } else {
            redirectUri = JapIds.getIdsConfig().getConfirmPageUrl();
        }

        return new IdsResponse<String, Object>()
            .data(ObjectUtils.appendIfNotEndWith(redirectUri, "?") + request.getQueryString());
    }
}
