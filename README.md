<p align="center">
	<img src="https://images.gitee.com/uploads/images/2021/0115/230947_5eb9e00d_784199.png" width="300">
</p>
<p align="center">
	<strong>Just auth into any app</strong>
</p>
<p align="center">
	<a target="_blank" href="https://search.maven.org/artifact/me.zhyd.oauth/JustAuth/1.15.9/jar">
		<img src="https://img.shields.io/badge/Maven%20Central-1.0.0-blue" ></img>
	</a>
	<a target="_blank" href="https://gitee.com/yadong.zhang/JustAuth/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-LGPL%203.0-red" ></img>
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
	</a>
	<a target="_blank" href="https://apidoc.gitee.com/yadong.zhang/JustAuth/" title="API文档">
		<img src="https://img.shields.io/badge/Api%20Docs-1.0.0-orange" ></img>
	</a>
	<a target="_blank" href="https://justauth.wiki" title="参考文档">
		<img src="https://img.shields.io/badge/Docs-latest-blueviolet.svg" ></img>
	</a>
    <a target="_blank" href='https://gitee.com/fujieid/jap/stargazers'>
      <img src="https://gitee.com/yadong.zhang/JustAuth/badge/star.svg?theme=gvp" alt='star'></img>
    </a>
</p>
<p align="center">
	<strong>开源地址：</strong> <a target="_blank" href='https://gitee.com/fujieid/jap'>Gitee</a> | <a target="_blank" href='https://gitee.com/fujieid/jap'>Github</a>
</p>
<p align="center">
	<strong>官方网站：</strong> <a target="_blank" href='https://www.justauth.plus'>https://justauth.plus</a>
</p>

## JAP 是什么？

JAP 是**一款开源的认证中间件**，基于模块化设计，并且与业务高度解耦，使用起来非常灵活，开发者可以毫不费力地将 JAP 集成到任何 web 应用程序中，就像集成 JA 一样，简单方便。

JAP 要做的是为所有需要身份认证的应用提供一套标准的解决方案，集成所有 APP。方便开发者无缝对接任何第三方应用或者自有系统。

- JAP 口号：Just auth into any app!
- JAP 目标：让身份链接无处可藏
- JAP 价值：方便开发者无缝对接任何第三方应用或者自有系统，提高开发效率，减少代码维护成本
- JAP 愿景：以开源的方式，受惠于开源社区，赋能于开发者。使之成为开发者生态内必不可少的“基础设施”，以期形成新的技术标准。

## JAP 有什么特点？

- 开箱即用：API 设计趋近于白话，类似并参考 JustAuth
- 多平台：
    - 国内外数十家第三方平台（基于 JustAuth）
    - OAuth（OIDC） 协议的平台，内置国内外常见平台
    - SAML 协议的平台，内置国内外常见平台
- 业务解耦：JAP 不深入具体的业务，只将授权认证方面的功能抽象出一套标准的组件，方便任意系统快速对接
- 模块化：JAP 基于模块开发，基本做到，用哪种引哪种
- 统一标准：一切内置实现或者自定义的实现，都基于标准的策略
- 多语言支持：Java、Python、Go、Node等

## 适用于哪些场景？

- 新项目立项，你们需要研发一套包含登录、认证的系统
- 现有登录模块为自研，但是新一轮的技术规划中，你们想将登录认证模块重构，以更加灵活的架构适应后面的新需求，比如：集成 MFA 登录、集成 OAuth 登录等
- 你们的项目太多，每个项目都需要登录认证模块，想解决这种重复劳动的问题
- 从长远方面考虑，公司或组织或个人需要一套标准的、灵活的、功能全面的登录认证功能
- 你们不想将研发成本放到登录认证这种必须但想做完善又需要花费大量时间成本、人力成本的事情上，希望有一个中间件可以完美集成登录认证功能，使研发人员有更多的时间和精力投入到业务开发中，提高研发产能和研发效率
- 你们除了需要对接标准的身份提供商外，还有一些非标准的身份提供商，需要投入研发人员单独定制开发
- 你们企业种用到的开发语言较多，比如：Java、Python、Node等，每种语言对应的系统，都要使用不同语言实现相同的登录认证功能
- 你们需要研发一个支持 OAuth 登录的 Web 应用程序
- 你们想让自己的系统支持对外提供 OAuth 服务
- 你们需要研发一个支持 SAML 登录的 Web 应用程序，但又苦于 SAML 那庞大而繁琐的业务流程和配置
- 你们想让自己的系统支持对外提供 SAML 服务
- 你们想研发一个支持 LDAP 登录的程序，但又不知道如何入手
- 你们觉得传统的账号密码非常脆弱，所想让用户使用一次性的手机验证码或邮箱验证码进行登录
- 你们企业希望联合其现有的企业用户目录，以允许员工使用其现有的企业凭据登录各种内部和第三方应用程序。
- ...


## 开源推荐
- `JustAuth` 开箱即用的整合第三方登录的开源组件: [https://github.com/justauth/JustAuth](https://github.com/justauth/JustAuth)
- `spring-boot-demo` 深度学习并实战 spring boot 的项目: [https://github.com/xkcoding/spring-boot-demo](https://github.com/xkcoding/spring-boot-demo)
- `mica` SpringBoot 微服务高效开发工具集: [https://github.com/lets-mica/mica](https://github.com/lets-mica/mica)
- `pig` 宇宙最强微服务认证授权脚手架(架构师必备): [https://gitee.com/log4j/pig](https://gitee.com/log4j/pig)
- `SpringBlade` 完整的线上解决方案（企业开发必备）: https://gitee.com/smallc/SpringBlade
