# 通用导入 1.0版本

## 使用说明


### 1.导入pom

首先下载jar文件，地址在[这里](https://github.com/cuitpanfei/import/releases)

然后，将下载的jar文件打包
```cmd
    mvn install:install-file -Dfile=xxx-0.0.1-SNAPSHOT.jar -DgroupId=com.pfinfo -DartifactId=import -Dversion=0.0.1-SNAPSHOT
```

最后，导入pom依赖
```xml
<dependency>
	<groupId>com.pfinfo</groupId>
	<artifactId>import</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. 创建实体类
```java
import com.pfinfo.impor.annotation.ImportModel;
import com.pfinfo.impor.annotation.ModelField;


@ImportModel(sheetName = "终端")
public class TerminalInfo {
	
	@ModelField(columnName="名称")
	private String name;

}
```
### 3. Excel文件转为对应List集合

#### 1). 业务类（controller/service/Action/...）内部调用工具类对应的静态方法。

我这里就在controller层展示了，如下：

```java
public class ImportTerminalController {
	
	/**
	 * 将url路径对应的文件内容转换为list集合。
	 * @param fileUrl 文件 url 路径
	 * @return list集合。
	 */
	public List<TerminalInfo> importInfo(String fileUrl){
		return ImportUtil.getData(TerminalInfo.class, fileUrl);
	}

}
```

# 通用导入 1.1版本

## 更新说明：
<ol>
<li> 将数据转换时吞掉的异常抛出，只要有一行数据有问题，就直接终止导入操作.</li>
<li> 新增一个自定义文件存储路径的方法。</li>
</ol>
