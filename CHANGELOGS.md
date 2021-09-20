## v1.0.5 (2021-09-15)

- feat: Add `jap-http-api` module. (Gitee Issue [#I43ZS7](https://gitee.com/fujieid/jap/issues/I43ZS7))
- feat: Add `jap-ids-web` module. Package the filter of ids as a separate component.
- feat: add HTTP servlet adapter to decouple jakarta servlets. **Note [1]**
- feat: [jap-social] Support to bind the account of the third-party platform. (Gitee Issue [#I46J6W](https://gitee.com/fujieid/jap/issues/I46J6W))
- change: [jap-ids] scope changed to optional.
- change: Upgrade `simple-http` to 1.0.5.
- change: Upgrade `JustAuth` to 1.16.4.
- change: Optimize code.

**Note [1]:**

In versions prior to version 1.0.5 of jap, rely on the `HttpServletRequest`, `Cookie`, `HttpServletResponse`, and `HttpSession` under the `javax.servlet.http` package in `jakarta-servlet`, such as:

```java
// Interface provided by jap
public interface JapStrategy {
    default JapResponse authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
```

```java
// Use jap in spring framework
XxJapStrategy.authenticate(config, request, response);
```

In order to improve the adaptability of the framework, since version 1.0.5, JAP removed the dependency of `jakarta-servlet` and adopted a new set of interfaces (reference: [jap-http](https:gitee.comfujieidjap-http) ).

The developer needs to adapt the original request when calling the JAP interface.

For example, if the developer uses `jakarta-servlet`, then the `HttpServletRequest` needs to be adapted:

```java
// Use 1.0.5 or higher version of jap in spring framework
XxJapStrategy.authenticate(config, new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
```

----

- feat: 增加 `jap-http-api` 模块。 (Gitee Issue [#I43ZS7](https://gitee.com/fujieid/jap/issues/I43ZS7))
- feat: 增加 `jap-ids-web` 模块。 将 `jap-ids` 的过滤器打包为一个单独的组件。
- feat: 添加 HTTP servlet 适配器以解耦 jakarta servlet。**注[1]**
- feat: [jap-social] 支持绑定第三方平台账号，该版本将社会化登录和绑定账号独立开来，以使其更加使用与多场景。 (Gitee Issue [#I46J6W](https://gitee.com/fujieid/jap/issues/I46J6W))
- change: [jap-ids] `scope` 在各个流程中都更改为可选，遵循 RFC6749 规范。
- change: 升级 `simple-http` 的版本为 1.0.5.
- change: 升级 `JustAuth` 的版本为 1.16.4.
- change: 优化代码。

**注[1]:**

在 1.0.5 以前版本，jap 中依赖 `jakarta-servlet` 中 `javax.servlet.http` 包下的 `HttpServletRequest`、`Cookie`、`HttpServletResponse`、`HttpSession`，比如：

```java
// jap 提供的接口
public interface JapStrategy {
    default JapResponse authenticate(AuthenticateConfig config, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
```

```java
// 在spring框架中使用 jap
XxJapStrategy.authenticate(config, request, response);
```

为了提高框架适配性，自 1.0.5 版本开始，JAP 去掉了 `jakarta-servlet` 依赖，采用了一套全新的接口（参考：[jap-http](https://gitee.com/fujieid/jap-http)），开发者在调用 JAP 接口时需要对原 request 进行适配。

比如，开发者使用了 `jakarta-servlet`，那么需要对 `HttpServletRequest` 进行适配处理：

```java
// 在spring框架中使用 1.0.5 或更高级版本的 jap
XxJapStrategy.authenticate(config, new JakartaRequestAdapter(request), new JakartaResponseAdapter(response));
```


## v1.0.4 (2021-08-15)

- fix: [jap-ids] Support to generate custom token. (Gitee[#I3U1ON](https://gitee.com/fujieid/jap/issues/I3U1ON))
- fix: [jap-ids] Support custom verification of client_secret, such as: BCrypt, etc. (Gitee[#I44032](https://gitee.com/fujieid/jap/issues/I44032))
- feat: [jap-ids] When `IdsConfig#enableDynamicIssuer` is `true`, custom `context-path` is supported.
- fix: [jap-ids] Solve the problem of "After refreshing the token, the user information cannot be obtained with the new access token". ([#I3XHTK](https://gitee.com/fujieid/jap/issues/I3XHTK))
- feat: [jap-oauth2] `Oauth2Strategy` supports the following methods: `refreshToken`, `revokeToken`, `getUserInfo`
- fix: [jap-social] Cannot customize `JapCache` and `AuthStateCache` of `SocialStrategy` at the same time. (Github[#6](https://github.com/fujieid/jap/issues/6))
- fix: [jap-core] fix npe bug. (Github[#5](https://github.com/fujieid/jap/issues/5))
- doc: change the template of issue and PR

----

- fix: [jap-ids] 支持生成自定义 token（包含 access_token 和 refresh_token）。 (Gitee[#I3U1ON](https://gitee.com/fujieid/jap/issues/I3U1ON))
- fix: [jap-ids] 支持自定义验证 `client_secret`，适配多种场景，如：BCrypt 等。 (Gitee[#I44032](https://gitee.com/fujieid/jap/issues/I44032))
- feat: [jap-ids] 当启用 `IdsConfig#enableDynamicIssuer` 时，支持自定义 `context-path`
- fix: [jap-ids] 解决“刷新token后，用新的access_token无法获取用户信息”问题。 (Gitee[#I3XHTK](https://gitee.com/fujieid/jap/issues/I3XHTK))
- feat: [jap-oauth2] `Oauth2Strategy` 支持使用以下方法： `refreshToken`、`revokeToken`、`getUserInfo`
- fix: [jap-social] 无法同时自定义`SocialStrategy`的 `JapCache` and `AuthStateCache`.(Github[#6](https://github.com/fujieid/jap/issues/6))
- fix: [jap-core] 修复 `userId` 为空时 NPE 异常. (Github[#5](https://github.com/fujieid/jap/issues/5))
- doc: 更改 issue 和 pr 的模板

## v1.0.3 (2021-07-28)

- (**fix**): a bug of JwkUtil
- (**fix**): cannot refresh token
- (**pr**): Merge Gitee PR [#16](https://gitee.com/fujieid/jap/pulls/16) by [@lowis](https://gitee.com/lowis)
- (**issue**): Fix Gitee [#I3YWTD](https://gitee.com/fujieid/jap/issues/I3YWTD)
- Upgrade justauth to 1.16.2

## v1.0.2 (2021-05-24)

### New features

- **jap-ids**
  - Add the `enableDynamicIssuer` in `IdsConfig`. When `enableDynamicIssuer=true`, jap ids will automatically extract `issuer` from the currently requested domain name.
  - Add the `loginPageUrl` in `IdsConfig`:
    - `loginPageUrl`: login form page url
    - `loginUrl`: The api url for login
  - Add the `externalLoginPageUrl` in `IdsConfig`. when the login page is not provided by an authorized service (the login page is hosted by other services), you need to enable this configuration.
  - Add the `externalConfirmPageUrl` in `IdsConfig`. When the authorization confirmation page is not provided by an authorized service (the authorization confirmation page is hosted by other services), you need to enable this configuration.
  - Add the `authorizeAutoApproveUrl` in `IdsConfig`. When the authorize url contains `autoapprove=true`, it will not jump to the `confirmPageUrl`, but will jump directly to the `authorizeAutoApproveUrl`.
  - Add some scopes, such as `profile`, `address`, `read` and `write`.
  - Add the `uid` in the `OauthUtil#createAuthorizeUrl(String, IdsRequestParam)`.
  - Add the `IdsUserStoreService` interface to support custom operations on user data after login.
  - Add the `IdsPipeline` interface, developers can customize the process, currently only supports the process of customizing `IdsxxFilter` and `LoginEndpoint`.
  - Add `SPI` plugin mechanism

- **jap-social**
  - `SocialStrategy` provides methods of `refreshToken`, `revokeToken`, and `getUserInfo`
  
### Modified

- **jap**
  - `javax.servlet-api` -> `jakarta.servlet-api`
  
- **jap-ids**
  - Modify `IdsConfig.confirmUrl` to `confirmPageUrl`.
  - Modify the return value of `ApprovalEndpoint#getAuthClientInfo(HttpServletRequest)` to `IdsResponse<String, Map<String, Object>>`.
  - Modify the return value of `Ap provalEndpoint#authorize(HttpServletRequest)` to `IdsResponse<String, String>`.
  - Modify the return value of `AuthorizationEndpoint#agree(HttpServletRequest)` to `IdsResponse<String, String>`.
  - Modify the return value of `LoginEndpoint#signin(HttpServletRequest)` to `IdsResponse<String, String>`.
  - Modify the return value of `LogoutEndpoint#logout(HttpServletRequest)` to `IdsResponse<String, String>`.
  - Modify the comment content of `ClientDetail`.
  - Modify the return type of `IdsResponse#getData()` to the specified generic.
  - Remove `IdsScopeProvider#initScopes(List<IdsScope>)`.
  - When `response_type=id_token`, the resulting Claims are returned in the ID Token.
  - Optimize the process of `UserInfoEndpoint#getCurrentUserInfo(HttpServletRequest)`, Response UserInfo Claims using Scope Values.
  - Modify the `loginByUsernameAndPassword` and `getByName` methods of the `IdsUserService` interface, and add the `clientId` parameter, which can be used to distinguish multi-tenant scenarios

### PR

- Gitee
  - Merge PR [#11](https://gitee.com/fujieid/jap/pulls/11) by [@dreamlu](https://gitee.com/dreamlu)
  - Merge PR [#12](https://gitee.com/fujieid/jap/pulls/12) by [@sywd](https://gitee.com/sywd)
  - Merge PR [#13](https://gitee.com/fujieid/jap/pulls/13) by [@dreamlu](https://gitee.com/dreamlu)
  - Merge PR [#14](https://gitee.com/fujieid/jap/pulls/14) by [@dreamlu](https://gitee.com/dreamlu)

### Issue

## v1.0.1（2021-04-21）

### New features

- Add `com.fujieid.jap.core.util.RequestUtil`
- **Complete the development of the `jap-ids` module, and provide oauth services externally based on `jap-ids`**
  - **Supported features**：
    - Authorization Code Grant
    - Proof Key for Code Exchange
    - Implicit Grant
    - Resource Owner Password Credentials Grant
    - Client Credentials Grant
    - Refresh access token
    - Revoke access token
    - Get the currently authorized user
    - Verify login status
    - Abnormal prompt
    - Sign out
    - OpenID Connect Discovery
    - JWK Endpoint
    - Custom jwt encryption and decryption certificate
    - Support multiple response types, such as: `code`, `token`, `id token`, `id token token`, `code id token`, `code token`, `code id token token`
    - ...
  
For more details about the use of `jap-ids`, please refer to the sample project: [jap-ids-demo](https://gitee.com/fujieid/jap-ids-demo), or refer to the document: [IDS OAuth 2.0 服务端]( https://justauth.plus/ids/)

### Modified

- [jap-oidc] Optimize the `OidcStrategy#authenticate` method, cache the `OidcDiscoveryDto`, and reduce unnecessary http
  requests
- [jap-oidc] Optimize the code of `OidcUtil`, fix known bugs
- [jap-social] fix known bugs
- Refactor `com.fujieid.jap.core.cache.JapLocalCache`, implement timer manually, clean local cache regularly

### PR

- Merge Gitee PR [#9](https://gitee.com/fujieid/jap/pulls/9) by [@dreamlu](https://gitee.com/dreamlu)

### Issue

- Fix Gitee Issue [#I3DC7N](https://gitee.com/fujieid/jap/issues/I3DC7N)

## v1.0.1-alpha.1（2021-03-07）

### Modified

- Fix the description error in `JapErrorCode`
- Improve the `JapTokenHelper`

### PR

- Merge Gitee PR [#8](https://gitee.com/fujieid/jap/pulls/8)

## v1.0.1-alpha（2021-03-05）

### jap-core

#### New features

- Add `JapErrorCode` enumeration class to manage exception codes and exception prompts
- Add `JapResponse` class to standardize interface return content
- Add `JapTokenHelper` class to manage user tokens uniformly
- Add `JapContext` class to maintain jap context information
- Add `JapAuthentication` class, unified management of login status information and jap context information

#### Modified

- Package structure
  - Move `AuthenticateConfig`, `JapConfig` to `com.fujieid.jap.core.config` package
  - Move `JapUtil` to `com.fujieid.jap.core.util` package
- delete
  - Delete `JapCacheContextHolder`
  - Delete `JapUserStoreContextHolder`
- Code
  - Refactored `AbstractJapStrategy`, introduced `JapContext` and `JapAuthentication` classes
  - Refactor the `JapConfig` class, only retain the `sso` and `ssoConfig` attributes, and add the `tokenExpireTime`
    and `cacheExpireTime` attributes at the same time
  - Modify the default validity period of the cache in `JapCacheConfig` to 7 days
  - Modify the default content in the `JapUserService` interface class
  - Add the `void remove key( string key)` method to `JapCache`
  - Add `errorCode` and `errorMessage` attributes in `JapException` to facilitate the processing of exception
    information into unified format return data
  - Add the `token` attribute to `JapUser`, and the jap token will be automatically returned after login
  - In the `JapStrategy` interface, the return type of the `authenticate` method is changed to `JapResponse`, and the
    strategy methods of all modules return data in a unified format
  - Mark the `redirect` method with `@Deprecated` in the `JapUtil` class, and it may be deleted in the future. At the
    same time add the `createToken` method

### jap-oauth2

- Modify the `authenticate` method of `Oauth2Strategy` to return` JapResponse`

### jap-oidc

- Modify the `authenticate` method of `OidcStrategy` to return` JapResponse`

### jap-simple

- Modify the `authenticate` method of `SimpleStrategy` to return` JapResponse`

### jap-social

- Modify the `authenticate` method of `SocialStrategy` to return` JapResponse`

### jap-sso

- Modify the return value of the `JapSsoHelper#login` method to the jap token of the current user
- Add `JapSsoUtil`
- In the `japSsoConfig` class, delete the `login url` and `logout url` attributes

### Other

- Add some unit tests

## v1.0.0（2021-02-18）

### New features

- added `jap-mfa` module to realize TOTP verification
- The `logout` method is added to the `JapUserStoreContextHolder` to support clearing cookies and sessions
- added test cases

### Modified

- Updated `jap.sh`, support a variety of common commands
- The `options` attribute in `JapConfig` is deleted, and the `justathConfig` attribute is added to `SocialConfig`
- Change the name of `RemberMeDetailsUtils` to `RembermeUtils`
- Move the `Oauth2Strategy#checkOauthConfig()` and `Oauth2Strategy#isCallback()` to the `Oauth2Util`

### Other

- Improved code
- Reconstruct the `SimpleConfig`, and move the unnecessary configuration items and business logic to
  the `RememberMeUtils`

## v1.0.0-alpha.1（2021-02-01）

### New features

- Add cache module `com.fujieid.jap.core.cache.JapCache`
- Add 'state' verification logic in `jap-oauth2`
- Add some `package-info.java`

### Modified

- Revision notes
- To solve the problem that 'codeverifier' in 'pkceutil' can only be cached locally
- Upgrade `simple-json` to `0.0.2`

### other

- Fix Javadoc compilation failure

## 1.0.0-alpha（2021-01-28）

JA Plus(JAP) is an open source authentication middleware, it is highly decoupled from business code and has good
modularity and flexiblity. Developers could integrate JAP into web applications effortlessly.

## Completed

- [login of username-password](https://justauth.plus/quickstart/jap-simple.html)
- [login of Social](https://justauth.plus/quickstart/jap-social.html)
- [login of OAuth 2.0](https://justauth.plus/quickstart/jap-oauth2.html)
- [login of oidc](https://justauth.plus/quickstart/jap-oidc.html)
- [SSO](https://justauth.plus/quickstart/jap-sso.html)

