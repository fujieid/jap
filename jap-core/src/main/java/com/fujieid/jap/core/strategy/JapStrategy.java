package com.fujieid.jap.core.strategy;

import com.fujieid.jap.core.AuthenticateConfig;
import com.fujieid.jap.core.exception.JapStrategyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The unified implementation interface of JAP Strategy, which must be implemented for all specific business policies.
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021/1/11 14:27
 * @since 1.0.0
 */
public interface JapStrategy {

    /**
     * This function must be overridden by subclasses.  In abstract form, it always throws an exception.
     *
     * @param config  Jap Strategy Configs
     * @param request The request to authenticate
     * @param response The response to authenticate
     */
    default void authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        throw new JapStrategyException("JapStrategy#authenticate must be overridden by subclass");
    }
}
