#通用导入 1.0版本

##使用说明


### 1.导入pom
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


@ImportModel(sheetName = "床垫")
public class TerminalInfo {
	
	@ModelField(columnName="名称")
	private String name;

}
```
### 3. Excel文件转为对应List集合

#### 1. 启动类上使用注解@EnableImportUtil

```java
@EnableImportUtil
@SpringBootApplication
public class ImportSpringApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ImportSpringApplication.class, args);
	}
}
```
#### 2. 业务类（controller/service/Action/...）内部调用工具类那只方法。

我这里就在controller层展示了，如下：

```java
@RestController
public class ImportTerminalController {
	
	/**
	 * 如果要使用ImportUtil，启动类上必须使用注解：
	 *  <code>@EnableImportUtil</code> ，否则，将导致应用无法启动。
	 */
	@Autowired
	private ImportUtil importUtil;
	
	/**
	 * 将url路径对应的文件内容转换为list集合。
	 * @param fileUrl 文件 url 路径
	 * @return list集合。
	 */
	@GetMapping({"","/","/importInfo"})
	public List<TerminalInfo> importInfo(String fileUrl){
		return importUtil.getData(TerminalInfo.class, fileUrl);
	}

}
```

