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
package com.fujieid.jap.sso;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class JapMfaTest {

    public static final String username = "japname";
    public static final String issuer = "japissue";

    public static void main(String[] args) {
        JapMfa japMfa = new JapMfa(new JapMfaServiceImpl());

        // 以下三种方式，任选一个测试
        // 1. 生成 file
//        File otpQrCodeFile = japMfa.getOtpQrcodeFile(username, issuer);
//        System.out.println(otpQrCodeFile);

        // 生成 base64 字符串
//        String otpQrCodeBase64 = japMfa.getOtpQrcodeFileBase64(username, issuer, true);
//        System.out.println(otpQrCodeBase64);

        // 生成 url 链接
        String otpQrCodeUrl = japMfa.getOtpQrCodeUrl(username, issuer);
        System.out.println(otpQrCodeUrl);

        varifyCode(japMfa);
    }

    private static void varifyCode(JapMfa japMfa) {
        String secretKey = japMfa.getSecretKey(username);

        System.out.println("1. 你需要打开生成的文件（或者将 Base64 字符串直接粘贴到浏览器地址会回车）");
        System.out.println("2. 然后使用 OTP 工具扫描二维码");
        System.out.println("3. 在控制台输入 code");
        Scanner scanner = new Scanner(System.in);
        Integer consoleInput = null;
        while (!(consoleInput = scanner.nextInt()).equals(-1)) {

            System.out.println(japMfa.getAuthenticator().getTotpPassword(secretKey));
            System.out.println(japMfa.getAuthenticator().getTotpPasswordOfUser(username));

            boolean verifyByUsernameResult = japMfa.verifyByUsername(username, consoleInput);
            System.out.println(new Date() + " 通过用户名检验 code 的结果：" + (verifyByUsernameResult ? "通过" : "code 错误"));

            boolean verifyBySecretResult = japMfa.verifyBySecret(secretKey, consoleInput);
            System.out.println(new Date() + " 通过 secretKey 检验 code 的结果：" + (verifyBySecretResult ? "通过" : "code 错误"));
        }
        System.out.println("结束...");
    }

    public static class JapMfaServiceImpl implements JapMfaService {

        public static String secretKey = "";

        /**
         * 根据帐号查询 secretKey
         *
         * @param userName 申请 secretKey 的用户
         * @return secretKey
         */
        @Override
        public String getSecretKey(String userName) {
            return secretKey;
        }

        /**
         * 将 secretKey 关联 userName 后进行保存，可以存入数据库也可以存入其他缓存媒介中
         *
         * @param userName       用户名
         * @param secretKey      申请到的 secretKey
         * @param validationCode 当前计算出的 TOTP 验证码
         * @param scratchCodes   scratch 码
         */
        @Override
        public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
            // 将 secretKey 关联 userName 后存入数据库（或者缓存中，视业务逻辑为主）
            System.out.println("[saveUserCredentials] userName：" + userName);
            System.out.println("[saveUserCredentials] secretKey：" + secretKey);
            System.out.println("[saveUserCredentials] validationCode：" + validationCode);
            JapMfaServiceImpl.secretKey = secretKey;
        }
    }
}
