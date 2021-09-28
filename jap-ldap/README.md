https://datatracker.ietf.org/doc/html/rfc2307
https://datatracker.ietf.org/doc/rfc2307/

https://www.openldap.org/faq/data/cache/629.html

http://gurolerdogan.blogspot.com/2010/03/ssha-encryption-with-java.html

https://www.thinbug.com/q/31401353

ext_des：
- https://hotexamples.com/examples/-/-/randomSalt/php-randomsalt-function-examples.html
- https://phpbb.itnotetk.com/viewtopic.php?t=452

## 测试 LDAP 的密码匹配器

1. 可以按照下面的方式启动 ldap，然后创建指定用户，创建用户的时候密码加密方式注意切换。然后在 `com.fujieid.jap.ldap.template.LdapDefaultTemplateTest` 中进行在线测试
2. 直接在 `com.fujieid.jap.ldap.password.LdapPasswordUtilTest` 中参考 `checkPassword` 方法写对应的验证规则

## 本地启动 ldap + UI 管理后台

.env
```text
LDAP_DIR=D://var/ldap
```

docker-compose.yml
```yaml
version: '3.3'
services:
  # MySQL
  ldap:
    image: osixia/openldap
    container_name: ldap
    hostname: ldap
    ports:
      - 389:389
      - 636:636
    environment:
      # LDAP 组织名称
      LDAP_TLS: "false"
      # LDAP 组织名称
      LDAP_ORGANISATION: test
      # LDAP 域名
      LDAP_DOMAIN: test.com
      # LDAP 密码，默认登录用户名：admin
      LDAP_ADMIN_PASSWORD: 123456
      LDAP_CONFIG_PASSWORD: 123456
    volumes:
      # 数据库存储目录
      - ${LDAP_DIR}/data:/var/lib/ldap
      # 配置文件目录
      - ${LDAP_DIR}/conf:/etc/ldap/slapd.d
    restart: always
  # Redis
  ldap-admin:
    image: osixia/phpldapadmin
    container_name: ldap-admin
    hostname: ldap-admin
    environment:
      # 禁用HTTPS
      PHPLDAPADMIN_HTTPS: "false"
      # LDAP 的 IP 或者域名
      PHPLDAPADMIN_LDAP_HOSTS: ldap
    ports:
      - 19999:80
    restart: always
    depends_on:
      - ldap
    links:
      - ldap
```

启动后访问：[http://localhost:19999/index.php](http://localhost:19999/index.php)

账号：cn=admin,dc=test,dc=com
密码：123456