## v1.0.1（2021-04-**）

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

