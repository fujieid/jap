package com.fujieid.jap.httpapi.util;

/**
 * URLUtil
 *
 * @author zhihai.yu (mvbbb(a)foxmail.com)
 * @version 1.0.0
 * @since 1.0.5
 */
public class URLUtil {

    private static final String HTTPS_PREFIX = "https://";
    private static final String HTTP_PREFIX = "http://";

    /**
     * Get relative uri from uri.
     * https://www.google.com ==>> www.google.com
     *
     * @param uri URI with protocol. For example: https://www.google.com
     * @return Return only the domain name part in the uri
     */
    public static String getRelativeUri(String uri) {
        if (uri == null) {
            return null;
        }
        String relativeUri = null;
        if (uri.startsWith(HTTPS_PREFIX)) {
            relativeUri = uri.replaceFirst(HTTPS_PREFIX, "");
        } else if (uri.startsWith(HTTP_PREFIX)) {
            relativeUri = uri.replaceFirst(HTTP_PREFIX, "");
        } else {
            relativeUri = uri;
        }
        relativeUri = relativeUri.substring(relativeUri.indexOf('/'));
        return relativeUri;
    }
}
