# commons-sms-verifycode-starter
短信验证码starter。

# 规则：
- 每个手机号在一段时间内最多发一次，默认60秒内
- 验证码最多使用若干次，然后作废，默认3次

# 配置
```
sms:
  verifycode:
    length: 4
    source: 0123456789
    timeout: 2m
    max-used: 3
    key-prefix: 3
    interval-in-mills: 60000
```