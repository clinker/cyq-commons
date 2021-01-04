# commons-webmvc-starter

Spring Boot MVC项目starter。

# Jackson
- 支持`java.util.time`
- 时区设为系统时区
- 区域设为JVM默认区域

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


