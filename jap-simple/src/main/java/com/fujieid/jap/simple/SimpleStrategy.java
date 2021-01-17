package com.fujieid.jap.simple;

import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapUserException;
import com.fujieid.jap.core.strategy.AbstractJapStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The local authentication strategy authenticates requests based on the credentials submitted through an HTML-based
 * login form.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 17:53
 * @since 1.0.0
 */
public class SimpleStrategy extends AbstractJapStrategy {

    /**
     * Initialization strategy
     *
     * @param japUserService Required, implement user operations
     */
    public SimpleStrategy(JapUserService japUserService, JapConfig japConfig) {
        super(japUserService, japConfig);
    }

    @Override
    public void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {

        if (this.checkSession(request, response)) {
            return;
        }

        // Convert AuthenticateConfig to SimpleConfig
        this.checkAuthenticateConfig(config, SimpleConfig.class);
        SimpleConfig simpleConfig = (SimpleConfig) config;

        UsernamePasswordCredential credential = this.doResolveCredential(request, simpleConfig);

        JapUser user = japUserService.getByName(credential.getUsername());
        if (null == user) {
            throw new JapUserException("The user does not exist.");
        }

        boolean valid = japUserService.validPassword(credential.getPassword(), user);
        if (!valid) {
            throw new JapUserException("Passwords don't match.");
        }

        this.loginSuccess(user, request, response);
    }

    private UsernamePasswordCredential doResolveCredential(HttpServletRequest request, SimpleConfig simpleConfig) {
        String username = request.getParameter(simpleConfig.getUsernameField());
        String password = request.getParameter(simpleConfig.getPasswordField());
        if (null == username || null == password) {
            throw new JapUserException("Missing credentials");
        }
        return new UsernamePasswordCredential()
                .setUsername(username)
                .setPassword(password);
    }
}
