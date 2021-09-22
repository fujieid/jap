> 本项目主要为了配合 `jap-ids` 使用，内置了 `IdsAccessTokenFilter`（Access Token 验权过滤器） 和 `IdsUserStatusFilter`（用户登录状态过滤器）
> 如果不需要该项目中的过滤器，开发者可以自己实现。

完整示例代码参考：[https://gitee.com/fujieid/jap-ids-demo](https://gitee.com/fujieid/jap-ids-demo)


## 配置过滤器

`jap-ids` 默认提供了两类过滤器：
- Access Token 验权过滤器
- 用户登录状态过滤器

以本项目为例，配置以下两个过滤器：

### Access Token 验权过滤器
```java
@Bean
public FilterRegistrationBean<IdsAccessTokenFilter> registeraccessTokenFilter() {
    FilterRegistrationBean<IdsAccessTokenFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new IdsAccessTokenFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("ignoreUrl",
            "/," +
            "/oauth/login," +
            "/oauth/error," +
            "/oauth/confirm," +
            "/oauth/authorize," +
            "/oauth/token," +
            "/oauth/check_session," +
            "/oauth/registration," +
            "/.well-known/jwks.json," +
            "/.well-known/openid-configuration"
    );
    registration.setName("IdsAccessTokenFilter");
    registration.setOrder(1);
    return registration;
}
```

### 用户登录状态过滤器
```java
@Bean
public FilterRegistrationBean<IdsUserStatusFilter> registerUserStatusFilter() {
    FilterRegistrationBean<IdsUserStatusFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new IdsUserStatusFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("ignoreUrl",
            "/," +
            "/oauth/login," +
            "/oauth/error," +
            "/oauth/confirm," +
            "/oauth/authorize," +
            "/oauth/token," +
            "/oauth/check_session," +
            "/oauth/registration," +
            "/.well-known/jwks.json," +
            "/.well-known/openid-configuration"
    );
    registration.setName("IdsUserStatusFilter");
    registration.setOrder(1);
    return registration;
}
```

## 总结

基于以上步骤， 就可快速搭建起来一套本地化的 OAuth2.0 服务。更多功能，请参考 [帮助文档](https://justauth.plus/)
