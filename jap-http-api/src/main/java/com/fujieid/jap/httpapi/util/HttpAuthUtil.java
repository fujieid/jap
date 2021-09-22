package com.fujieid.jap.httpapi.util;

import com.fujieid.jap.httpapi.enums.HttpMethodEnum;
import com.fujieid.jap.httpapi.subject.HttpAuthResponse;
import com.xkcoding.http.HttpUtil;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.support.SimpleHttpResponse;

import java.util.List;
import java.util.Map;

/**
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public final class HttpAuthUtil {

    public static HttpAuthResponse sendRequest(String url,
                                               HttpMethodEnum method,
                                               Map<String, String> header,
                                               Map<String, String> params,
                                               String body) {
        SimpleHttpResponse response = null;
        switch (method) {
            case GET:
                response = HttpUtil.get(url, params, new HttpHeader(header), false);
                break;
            case POST:
                if (body == null || "".equals(body)) {
                    response = HttpUtil.post(url, params, new HttpHeader(header), false);
                } else {
                    response = HttpUtil.post(url, body, new HttpHeader(header));
                }
                break;
            default:
                break;
        }
        if (response == null) {
            return null;
        }
        String responseBody = response.getBody();
        Map<String, List<String>> headers = response.getHeaders();
        return new HttpAuthResponse(response.isSuccess(), responseBody, headers);
    }
}

