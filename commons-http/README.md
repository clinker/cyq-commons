# commons-http

HTTP 客户端。

# 特性
- 基于OKHttp3 v4.9.0
- 使用连接池
- 并发连接数量可控

# 用法
```java
    HttpConn httpConn = new HttpConnClient();
    httpConn.postJson(...);
    httpConn.get(...);
    ...
```