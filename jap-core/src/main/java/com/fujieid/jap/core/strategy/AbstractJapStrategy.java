package com.fujieid.jap.core.strategy;

import com.fujieid.jap.core.JapConfig;
import com.fujieid.jap.core.JapConst;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.exception.JapException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * General policy handling methods and parameters, policies of other platforms can inherit
 * {@code AbstractJapStrategy}, and override the constructor
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/12 14:01
 * @since 1.0.0
 */
public abstract class AbstractJapStrategy implements JapStrategy {

    /**
     * Abstract the user-related function interface, which is implemented by the caller business system.
     */
    protected JapUserService japUserService;
    /**
     * Jap configuration.
     */
    protected JapConfig japConfig;

    /**
     * `Strategy` constructor.
     *
     * @param japUserService japUserService
     * @param japConfig      japConfig
     */
    public AbstractJapStrategy(JapUserService japUserService, JapConfig japConfig) {
        this.japUserService = japUserService;
        this.japConfig = japConfig;
    }

    /**
     * Verify whether the user logs in. If so, jump to {@code japConfig.getSuccessRedirect()}. Otherwise, return {@code false}
     *
     * @param request  Current Authentication Request
     * @param response Current response
     * @return boolean
     */
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) {
        if (japConfig.isSession()) {
            HttpSession session = request.getSession();
            JapUser sessionUser = (JapUser) session.getAttribute(JapConst.SESSION_USER_KEY);
            if (null != sessionUser) {
                try {
                    response.sendRedirect(japConfig.getSuccessRedirect());
                    return true;
                } catch (IOException e) {
                    throw new JapException("JAP failed to redirect via HttpServletResponse.", e);
                }
            }
        }
        return false;
    }
}
