# commons-upload-starter

Spring Boot MVC 文件上传starter。

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


