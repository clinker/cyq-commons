# commons-transformer
Java Bean之间复制属性。

# 特性
使用`org.springframework.beans.BeanUtils`进行Bean复制。

# 用法
- 无需定制
  + 转换单个Bean  
  `BeanTransformerUtils.transform(SOURCE, Class<TARGET>)`
  + 转换集合  
  `BeanTransformerUtils.transform(List<SOURCE>, Class<TARGET>)`

- 定制
  + 转换单个Bean  
  实现`BeanTransformer`,覆写  `customCopyProperties(SOURCE, TARGET)`
  + 转换集合
  实现`BeanTransformer`,覆写  `customTransform(List<SOURCE>, List<TARGET>)`

- 复制时需要其他Bean
  + 转换单个Bean  
  实现`BeanExtraTransformer`，覆写`customCopyProperties(SOURCE, TARGET, EXTRA)`
  + 转换集合  
  实现`BeanExtraTransformer`,覆写  `customTransform(List<SOURCE>, List<TARGET>, EXTRA)`
