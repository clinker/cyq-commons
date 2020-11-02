# commons-webmvc-starter

Spring Boot MVC项目starter。

# Jackson
- 支持`java.util.time`
- 时区设为系统时区
- 区域设为JVM默认区域

# 文件上传
- 默认禁用
- 开启
cyq.upload.enabled=true

## 特性
客户端通过HTTP上传文件，本系统将文件保存到本地，返回文件标识和web访问路径。

其中，生成子目录的策略是可变的，文件上传后重命名的策略是可变的。

## 设计
- 存储路径（storage path）
  + 系统配置的存储路径，可以是本地文件系统路径或第三方存储路径。

  - 本地文件系统路径
    + 绝对路径或相对路径。
    + 相对路径是相对于webapp的real path。

  - 第三方存储路径
    一般是http（s）路径。

- 文件相对路径（path）
  相对于存储路径。由webapp按照某种规则生成。

- Web访问前缀 （uri prefix）
  如果以http（s）开头，则是完整的URI。
  否则，相对于webapp的context path。

- Web访问路径（uri）
  web访问路径 = web访问前缀 + 文件相对路径

- 路径举例说明
	假设：
	（1）系统配置的存储绝对路径为：/home/adam/files
	（2）系统创建目录的策略为“年/月”
	（3）系统配置的web访问前缀为：https://www.bar.com/static
	（4）系统将该文件重命名为：c0f7a2dc556b7fc7ce0fca5b08a2da17.txt
	则在2099年1月，客户端上传文件“foo.txt”，
	系统将该文件保存到：/home/adam/files/2099/1/c0f7a2dc556b7fc7ce0fca5b08a2da17.txt
	并返回：
```
	{
		"id":  "cdc8cc61029113a42cfe364092cf6428",
		"path": "2099/1/c0f7a2dc556b7fc7ce0fca5b08a2da17.txt",
		"uri":  "https://www.bar.com/static/2099/1/c0f7a2dc556b7fc7ce0fca5b08a2da17.txt"
	}
```

application.yml示例：
```
cyq:
  upload:
    enabled: true
    storage-path: f:/upload
    uri-prefix: http://127.0.0.1/upload

spring:
    servlet:
      multipart:
        max-file-size: 20MB
        max-request-size: 30MB
```

# 验证器
- 手机号码，规则：以1开头的11位数字。null和空字符串视为有效。
- 日期，格式：yyyy-MM-dd HH:mm:ss。null和空字符串视为有效。
- 日期时间，格式：yyyy-MM-dd。null和空字符串视为有效。
- 身份证号码。

# 获取远程地址
见`RemoteAddressUtils.resolve(HttpServletRequest)`。

# 设置响应不缓存
见`ResponseNoCacheUtils.noCache(HttpServletResponse)`。

# 异常统一处理
- 状态码
  + 200: 正常
  + 400: 业务异常
  + 401: 未授权
  + 403: 无权限
  + 500: 服务器异常

- 异常响应数据格式为JSON
```
{
    "code": "xx",
    "message: "yy"
}
```

- 默认开启
- 禁用
cyq.webmvc.error-handling=false

# 记录请求和响应详细信息
见`RequestDetailLoggingFilter`。
- 默认禁用
- 开启
cyq.webmvc.request-logging=true


