# cyq-commons
类库大全。

# 依赖版本
与Spring Boot保持一致。

# POM/BOM
## 非Spring项目
继承commons-version。

## Spring项目
继承commons-spring。

## Spring Boot项目
继承commons-spring-boot。参见commons-spring-boot-deps。

## Spring Boot MVC项目
继承commons-spring-boot-web。

## Spring AutoConfigure项目
继承commons-spring-boot-autoconfigure。

# 类库
- commons-http: HTTP客户端
- commons-pinyin：汉字拼音
- commons-transformer：Java Bean复制
- commons-util：无第三方依赖的工具类集合

# Spring Boot Starter
- commons-mybatis-plus-starter：定制MyBatis Plus
- commons-security-starter: Restful认证、动态授权
- commons-sms-verifycode-starter：短信验证码
- commons-webmvc-starter：定制Spring MVC