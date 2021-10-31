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
package com.fujieid.jap.core.util;

import com.fujieid.jap.http.JapHttpResponse;

import java.security.CodeSource;

/**
 * The tool class of Jap only provides static methods common to all modules
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class JapUtil {

    public static final String LOGO =
        "      _           _                 _   _     _____  _           \n" +
            "     | |         | |     /\\        | | | |   |  __ \\| |          \n" +
            "     | |_   _ ___| |_   /  \\  _   _| |_| |__ | |__) | |_   _ ___ \n" +
            " _   | | | | / __| __| / /\\ \\| | | | __| '_ \\|  ___/| | | | / __|\n" +
            "| |__| | |_| \\__ \\ |_ / ____ \\ |_| | |_| | | | |    | | |_| \\__ \\\n" +
            " \\____/ \\__,_|___/\\__/_/    \\_\\__,_|\\__|_| |_|_|    |_|\\__,_|___/\n";
    private static final String INIT_VERSION = "1.0.0";

    @Deprecated
    public static void redirect(String url, JapHttpResponse response) {
        redirect(url, "JAP failed to redirect via HttpServletResponse.", response);
    }

    @Deprecated
    public static void redirect(String url, String errorMessage, JapHttpResponse response) {
        response.redirect(url);
    }

    public static void printBanner() {
        String separator = System.getProperty("line.separator");
        String banner = separator + LOGO +
            separator + "JustAuthPlus (v" + getVersion() + "), An open source login authentication middleware." +
            separator + "Dev Doc：https://justauth.plus" +
            separator + "Gitee：https://gitee.com/fujieid/jap" +
            separator + "Github：https://github.com/fujieid/jap";
        System.out.println(banner);
    }

    /**
     * 获取当前 jap 的依赖版本
     *
     * @return version
     */
    public static String getVersion() {
        try {
            Class clazz = JapUtil.class;
            String version = clazz.getPackage().getImplementationVersion();
            if (version == null || version.length() == 0) {
                version = clazz.getPackage().getSpecificationVersion();
            }

            if (version == null || version.length() == 0) {
                CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
                if (codeSource != null) {
                    String file = codeSource.getLocation().getFile();
                    if (file != null && file.endsWith(".jar")) {
                        file = file.substring(0, file.length() - 4);
                        int i = file.lastIndexOf(47);
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        i = file.indexOf("-");
                        if (i >= 0) {
                            file = file.substring(i + 1);
                        }
                        while (file.length() > 0 && !Character.isDigit(file.charAt(0))) {
                            i = file.indexOf("-");
                            if (i < 0) {
                                break;
                            }
                            file = file.substring(i + 1);
                        }
                        version = file;
                    }
                }
            }

            return version != null && version.length() != 0 ? version : INIT_VERSION;
        } catch (Throwable var6) {
            return INIT_VERSION;
        }
    }
}
