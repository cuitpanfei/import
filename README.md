# 通用导入 1.0版本

## 使用说明


### 1.导入pom

#### 1) 首先下载pure分支源码并本地打jar包,然后进入target,就可以看见jar包了
```cmd
> git clone https://github.com/cuitpanfei/import.git -b pure
> cd import
> mvn clean package
> cd target
```

#### 2) 然后，将编译好的的jar文件打包
- 请注意填写指定版本号
```cmd
    mvn install:install-file -Dfile=import-${version}.jar -DgroupId=com.pfinfo -DartifactId=import -Dversion=${version}
```

#### 3) 当然你也可以在第一阶段的第三步执行下面的命令:
```cmd
> mvn clean install
```
这样叫你就可以跳过第二阶段

#### 4) 最后，导入pom依赖
```xml

<dependency>
	<groupId>com.pfinfo</groupId>
	<artifactId>import</artifactId>
	<version>${version}</version>
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



# 通用导入 1.2版本

## 更新说明：
<ol>
<li> 更新版本号为：0.0.2-pure.</li>
<li> 修改容器类修饰，新增一个容器访问类：BeanCatchContext.java</li>
</ol>
