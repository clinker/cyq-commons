# commons-security-starter

使用Spring Security实现Restful认证、动态授权。

# 特性
- 使用Token保持会话
    + 客户端通过HTTP header传递token
    + Token默认为UUID字符串
    + Token过期时间自动延长，类似HTTP session
- RESTful请求和响应
    + 登录请求、响应
    + 退出登录响应
    + 未认证响应
    + 未授权响应
- 自定义`UserDetailsService`，从MySQL读取用户数据
- 账号、角色、权限，都通过`serviceId`对进行租户隔离
- 对不需要认证的URL，将AuthPermissin的ignored设为true
- 动态对URL进行授权，授权数据存在MySQL
    + 用ConcurrentHashMap缓存授权数据，并订阅redis消息，实现缓存刷新。发布刷新事件：`127.0.0.1:6379> publish authz:refresh 1`

# 建议依赖
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>
	<groupId>io.lettuce</groupId>
	<artifactId>lettuce-core</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

# 配置
- TenantProperties
- AuthzProperties
- TokenProperties

示例中的值均为默认值：
```
auth:
  service-id: demo
  token:
    timeout: 30m
    prefix: Authorization
    headerValuePrefix: Bearer 
authz:
  cache-topic: authz:refresh
```

# 会话
## 禁用Session

## 使用Token保持会话
- 自定义生成规则。默认UUID，见`TokenGeneratorUuid`
- 自定义header名称、header值前缀、token有效期、token在redis的key前缀， 见`TokenProperties`
- 自定义存储。本例用redis。每个登录存两条：
    + 一个value，存放token，key是${auth.token}:${token}，值是`com.github.clinker.commons.security.token.TokenValue`
    + 一个set，存放account的所有token，key是${auth.token}:accounts:${accountId}，值是token的key

## 客户端通过HTTP header保持会话
- `TokenProperties`定义header名称。默认`Authorization`
- `TokenProperties`定义header值前缀。默认`Bearer ${token}`

## 关闭csrf
- 关闭csrf，因为postman之类的客户端无法获得csrf token而导致没有权限访问接口

# 账号
## 账号在MySQL
- 见`AuthAccount`

## 自定义`UserDetails`
- 见`AuthAccountUserDetails`

## 自定义`UserDetailsService`
- 见`AuthAccountUserDetailsServiceImpl`

# RESTful
## 登录请求
- 见`LoginForm`
```json
{
    "username": "zhangsan",
    "password": "87654321"
}
```

## 登录成功响应
- 见`LoginResult`
```json
{
    "accountId": "4e4000ba-4c32-4cd0-8a02-4bd7d38e8f38",
    "username": "zhangsan"
}
```
- 响应header
```
Authorization: e72b2bd1-0cb7-4f40-81b9-c7b6f4a32f4a
```

## 登录失败响应
- 见`RestAuthenticationFailureHandler`
- 返回状态码400和JSON格式消息
```
  {
    "message": "账号或密码错误",
    "code": "LoginError.FAIL"
  }
```

## 未认证401响应
- 见`HttpStatusEntryPoint`，返回状态码401

## 未授权403响应
- 见`RestAccessDeniedHandler`，返回状态码403

# 角色
- `UserDetails`的`getAuthorities()`代表角色
- 角色是字符串，即AuthRole的identifier
- 超级角色

# 授权
- 权限是：URL + HTTP method
    + HTTP method：逗号分隔，不区分大小写，`*`表示全部
    + 所有URL均要求授权
- 不检查认证和授权的URL，则设置`ignored`为`true`
- 角色关联权限
- 超级角色允许访问所有URL

# 数据库
## 账号表
```
CREATE TABLE `auth_account` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `service_id` varchar(50) NOT NULL COMMENT '所属服务ID，即租户',
  `username` varchar(60) NOT NULL COMMENT '登录名',
  `password` varchar(80) NOT NULL COMMENT '登录密码',
  `disabled` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否禁用',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像路径，本地UNIX格式或URI',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_service_username` (`service_id`,`username`)
) COMMENT='账号';

```

## 角色表
```
CREATE TABLE `auth_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `service_id` varchar(50) NOT NULL COMMENT '所属服务ID，即租户',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `identifier` varchar(50) NOT NULL COMMENT '标识',
  `super_role` tinyint NOT NULL DEFAULT '0' COMMENT '是否是超级用户',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序，升序',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `creation_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_service_identifier` (`service_id`,`identifier`)
) COMMENT='角色';

```

## 权限表
```
CREATE TABLE `auth_permission` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `service_id` varchar(50) NOT NULL COMMENT '所属服务ID，即租户',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `url` varchar(50) NOT NULL COMMENT 'ANT风格URL',
  `method` varchar(50) NOT NULL COMMENT 'HTTP方法，逗号分隔，不区分大小写。*表示全部',
  `ignored` tinyint NOT NULL DEFAULT '0' COMMENT '不检查认证和授权',
  `sort` int unsigned NOT NULL DEFAULT '0' COMMENT '排序，升序',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_service_name` (`service_id`,`name`)
) COMMENT='权限';
```

## 账号与角色关联表
```
CREATE TABLE `auth_account_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `account_id` varchar(32) NOT NULL COMMENT '账号ID，关联auth_account表',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID，关联auth_role表',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `udx_account_role` (`account_id`,`role_id`),
  KEY `idx_role_id` (`role_id`)
) COMMENT='账号与角色的关联';
```

## 角色与权限的关联表
```
CREATE TABLE `auth_role_permission` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID，关联auth_role表',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID，关联auth_permission表',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `udx_role_permission` (`role_id`,`permission_id`)
) COMMENT='角色与权限的关联';
```
