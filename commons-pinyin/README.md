# commons-pinyin
处理汉字拼音。基[TinyPinyin](https://github.com/promeG/TinyPinyin)。

# 非中文
如果字符不是中文，则只是转为小写，例如：`我 们hello`的拼音为`wo mmenhello`、首字母为`wm hello`。

# 小写拼音
```
PinyinUtils.pinyin(str)
```

# 小写拼音，加分隔符
```
PinyinUtils.pinyin(str, separator)
```

# 小写拼音首字母
```
PinyinUtils.initials(str)
```

# 是否中文
```
PinyinUtils.isChinese(c)
```
