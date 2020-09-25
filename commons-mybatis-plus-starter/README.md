# commons-mybatis-plus-starter
MyBatis Plus Spring Boot Starter。

# 特性
- 引入MyBatis Plus的`mybatis-plus-boot-starter`
- MyBatis Plus的`IPage`复制

# MyBatis Plus定制
- 启用分页插件
- 关闭二级缓存
- 局部缓存改为语句级

# `IPage`复制用法
- 无需定制  
  `PageBeanTransformerUtils.transform(IPage<SOURCE>, Class<TARGET>)`

- 定制  
  实现`PageBeanTransformer`,覆写  `transform(IPage<SOURCE>)`
