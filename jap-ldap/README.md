## JAP LDAP

`jap-ldap` 是为了方便快速的接入 LDAP 协议而开发的组件。

## 本地启动 ldap + UI 管理后台

docker-compose.yml
```yaml
version: '3.3'
services:
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
      - /var/ldap/data:/var/lib/ldap
      # 配置文件目录
      - /var/ldap/conf:/etc/ldap/slapd.d
    restart: always
  # Redis
  ldap-admin:
    image: osixia/phpldapadmin
    container_name: ldap-admin
    hostname: ldap-admin
    environment:
      # 禁用HTTPS
      PHPLDAPADMIN_HTTPS: "false"
      # LDAP 的 IP 或者域名，此处为容器名
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

## LdapStrategy 使用方式

参考：[帮助文档](https://justauth.plus/quickstart/jap-ldap/)

## 参考资料

- https://datatracker.ietf.org/doc/html/rfc2307
- https://datatracker.ietf.org/doc/rfc2307/


- https://www.openldap.org/faq/data/cache/629.html


- http://gurolerdogan.blogspot.com/2010/03/ssha-encryption-with-java.html


- https://www.thinbug.com/q/31401353


- ext_des：
  - https://hotexamples.com/examples/-/-/randomSalt/php-randomsalt-function-examples.html
  - https://phpbb.itnotetk.com/viewtopic.php?t=452