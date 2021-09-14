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
package com.fujieid.jap.http.adapter.jakarta;

import com.fujieid.jap.http.JapHttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JakartaResponseAdapter implements JapHttpResponse {

    private final HttpServletResponse response;

    public JakartaResponseAdapter(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Get the actual source object
     *
     * @return Object
     */
    @Override
    public Object getSource() {
        return this.response;
    }

    /**
     * Delete cookie
     *
     * @param name cookie name
     * @return current response
     */
    @Override
    public JapHttpResponse delCookie(String name) {
        addCookie(null);
        return this;
    }

    /**
     * Add cookie
     *
     * @param name       name of the cookie.
     * @param value      value of this Cookie.
     * @param path       the path on the server to which the browser returns this cookie.
     * @param domain     domain name of this Cookie.
     * @param expiry     maximum age in seconds of this Cookie.
     * @param secure     send cookies only over a secure protocol, such as HTTPS or SSL.
     * @param isHttpOnly http only.
     * @return current response
     */
    @Override
    public JapHttpResponse addCookie(String name, String value, String path, String domain, int expiry, boolean secure, boolean isHttpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge(expiry);
        cookie.setSecure(secure);
        cookie.setHttpOnly(isHttpOnly);
        this.response.addCookie(cookie);
        return this;
    }

    /**
     * Set response status code
     *
     * @param status Response status code
     * @return current response
     */
    @Override
    public JapHttpResponse setStatus(int status) {
        this.response.setStatus(status);
        return this;
    }

    /**
     * Add response header
     *
     * @param name  name of the response header
     * @param value value of the response header
     * @return current response
     */
    @Override
    public JapHttpResponse addHeader(String name, String value) {
        this.response.addHeader(name, value);
        return this;
    }

    @Override
    public JapHttpResponse setContentType(String contentType) {
        this.response.setContentType(contentType);
        return this;
    }

    @Override
    public JapHttpResponse setContentLength(int len) {
        this.response.setContentLength(len);
        return this;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    /**
     * Returns the name of the character encoding (MIME charset) used for the body sent in this response
     * <p>
     * See RFC 2047 (http://www.ietf.org/rfc/rfc2047.txt) for more information about character encoding and MIME.
     *
     * @return a <code>String</code> specifying the name of the character encoding, for example, <code>UTF-8</code>
     */
    @Override
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    /**
     * Returns a {@link OutputStream} suitable for writing binary data in the response.
     *
     * @return a {@link OutputStream} for writing binary data
     * @throws IOException if an input or output exception occurred
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    /**
     * Redirect to url
     *
     * @param url Redirect url
     */
    @Override
    public void redirect(String url) {
        try {
            this.response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
