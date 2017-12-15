URL
代码生成器
http://127.0.0.1:8768/sys/generator.html
druid监控
http://127.0.0.1:8768/druid/index.html

要先登录  http://localhost:8768/login.html

后台管理
http://localhost:8768/admin.html


tomcat /bin下  catalina.bat  .sh
SET CATALINA_OPTS=-Dfile.encoding=UTF-8

打包
tomcat的servlet-api.jar
拷贝../jdk/jre//lib/ext/
	
	本框架所有工具类皆在com.css.base.utils包下
CacheUtils：提供除了Spring Cache注解操作缓存为，手动操作Cache信息的方法；
DateUtils：提供日期格式化字符串和字符串转日期的方法；
EncryptUtils：提供MD5、sha1等算法；
JavaUtils：可以方便的获取打包后，jar运行时所在目录；
PageUtils：分页封装类，支持使用PageHeolper工具查询后的返回值作为构建参数；
PingYinUtils：汉子转拼音；
Response：响应工具类，方便直接以JSON方式返回，以及文件下载；
SpringContextUtils：方便用来获取指定类型或名称的Bean实例；
StringUtils：含有常用的字符串处理工具；
UUIDUtils：用户生成UUID；
ZipUtils：方便对文件通过zip的方式压缩解压缩。