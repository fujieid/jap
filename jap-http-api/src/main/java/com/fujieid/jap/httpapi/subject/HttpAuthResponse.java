package com.fujieid.jap.httpapi.subject;

import java.util.List;
import java.util.Map;

public class HttpAuthResponse {
    private boolean success;
    private String body;
    private Map<String, List<String>> headers;

    public HttpAuthResponse(boolean success, String body, Map<String, List<String>> headers) {
        this.success = success;
        this.body = body;
        this.headers = headers;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpAuthResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpAuthResponse setBody(String body) {
        this.body = body;
        return this;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public HttpAuthResponse setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }
}
