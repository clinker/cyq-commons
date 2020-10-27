# commons-http

HTTP 客户端。

# 特性
- 基于Apache的httpclient5
- 使用连接池
- 并发连接数量可控

# 用法
```java
    HttpConn httpConn = new HttpConnClient(5);
    httpConn.post(...);
    httpConn.get(...);
    ...
    httpConn.close();
```