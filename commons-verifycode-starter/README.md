# commons-verifycode-starter
验证码starter。

# 规则：
- 每个键（例如：手机号）在一段时间内最多发一次，默认60秒内
- 验证码最多使用若干次，然后作废，默认3次

# 建议依赖
```xml
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
```
verifycode:
  length: 4
  source: 0123456789
  timeout: 2m
  max-used: 3
  key-prefix: 3
  interval-in-mills: 60000
```