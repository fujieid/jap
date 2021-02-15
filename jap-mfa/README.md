## JAP MFA

实现多因素认证中的 TOTP（Time based one-time password） 认证

## 使用方法

### 1. 引入依赖

```xml

<dependency>
  <groupId>com.fujieid</groupId>
  <artifactId>jap-mfa</artifactId>
  <version>{latest}</version>
</dependency>
```

### 2. 实现 `JapMfaService` 接口

```java
public static class JapMfaServiceImpl implements JapMfaService {

  /**
   * 根据帐号查询 secretKey
   *
   * @param userName 申请 secretKey 的用户
   * @return secretKey
   */
  @Override
  public String getSecretKey(String userName) {
    // do something 
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
    // do something 
  }
}
```

### 3. 初始化 `JapMfa`

```java
JapMfa japMfa = new JapMfa(new JapMfaServiceImpl());
```

### 4. 生成 TOTP 绑定二维码

`jap-mfa` 提供了四种创建二维码的方式:

1. 生成 `File` - `File otpQrCodeFile = japMfa.getOtpQrcodeFile(username, issuer);`
2. 生成图片的 `base64` 字符串（可直接通过 `img` 标签的 `src` 属性显示） - `String otpQrCodeBase64 = japMfa.getOtpQrcodeFileBase64(username, issuer, true);`
3. 生成可直接访问的图片 URL - `String otpQrCodeUrl = japMfa.getOtpQrCodeUrl(username, issuer);`
4. 直接通过 `HttpServletResponse` 返回图片 - `japMfa.createOtpQrcode(username, issuer, HttpServletResponse);`

其中，`username` 为当前需要绑定的用户名，`issuer` 一般为服务提供者，比如 `Baidu`、`baidu.com`、`JAP` 等等

### 5. 扫码绑定 TOTP

手机下载安装 MFA 工具，比如：Google authenticator、TOTP Authenticator等等，打开安装好的 APP，选择扫描二维码（或者 Scan QR Code）。然后扫描第 4 步生成的二维码文件即可完成绑定。

### 6. 校验 TOTP 动态码

`jap-mfa` 提供了两种校验 TOTP 动态码的方式:

1. 通过用户名校验 - `boolean verifyByUsernameResult = japMfa.verifyByUsername(username, consoleInput)`
2. 通过 secretKey 校验 - `boolean verifyBySecretResult = japMfa.verifyBySecret(secretKey, consoleInput);`

校验通过返回 `true`。