#### core-tools使用教程

1. pom引入core-tools包。

   > 两种方式
   >
   > 1. 将jar打包到本地maven仓库
   >
   >    ```Xml
   >    1.前往到jar包所在目录，执行以下代码
   >    mvn install:install-file "-Dfile=./core-tools-0.0.1-SNAPSHOT.jar" "-DgroupId=com.wj" "-DartifactId=core-tools"  "-Dversion=0.0.1-SNAPSHOT"  "-Dpackaging=jar"
   >
   >    2.引入pom依赖
   >    <dependency>
   >    	<groupId>com.wj</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>0.0.1-SNAPSHOT</version>
   >    </dependency>
   >    ```
   >
   > 2. 从本地文件引入
   >
   >    ```xml
   >    1.将jar包放到项目目录
   >    2.引入pom依赖
   >    <dependency>
   >    	<groupId>com.wj</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>0.0.1-SNAPSHOT</version>
   >    	<scope>system</scope>
   >    	<systemPath>${project.basedir}/lib/core-tools-0.0.1-SNAPSHOT.jar</systemPath>
   >    </dependency>
   >    ```
   >
   > 3. 从maven中央仓库引入
   >
   >    ```xml
   >    <dependency>
   >    	<groupId>com.wj</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>0.0.1-SNAPSHOT</version>
   >    	<scope>system</scope>
   >    </dependency>
   >    ```
   >
   >    ​

2. 在Application启动类里按需注册Bean对象

   ```java
   // 全局异常处理
   @Bean
   public ExceptionController exceptionController() {
     return new ExceptionController();
   }
   // redis工具
   @Bean
   public RedisLockHelper redisLockHelper() {
     return new RedisLockHelper();
   }
   // mybatis-plus 字段填充控制器，实现公共字段自动写入
   @Bean
   public MetaObjectHandler defaultMetaObjectHandler() {
     return new DefaultMetaObjectHandler();
   }
   // ======== 全局过滤器 让服务只能从网关调用 start ========
   @Bean
   public GlobalInterceptor globalInterceptor() {
     return new GlobalInterceptor();
   }

   // === 生成 auth-secretKey
   @Bean
   public RedisUUID redisUUID() {
     return new RedisUUID();
   }

   // === 过滤url配置
   @Bean
   public AuthIgnoreProperties authIgnoreProperties() {
     return new AuthIgnoreProperties();
   }
   // ======== 全局过滤器 让服务只能从网关调用 end ========
   ```

3. 按需编写配置类

   - JacksonConfig

     ```java
     // 全局配置序列化返回
     // Long 转换成 String 防止Long精度丢失
     // 日期格式化
     // 时区等等
     @Configuration
     public class JacksonConfig extends DefaultJacksonConfig {

     }
     ```

   - MybatisPlusConfig

     ```java
     /**
      * mybatis-plus配置
      * 注册分页插件、乐观锁插件等等
      */
     @EnableTransactionManagement
     @Configuration
     public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

     }
     ```

   - RedisConfig

     ```java
     /**
      * 设置redis序列化规则
      */
     @Configuration
     public class RedisConfig extends DefaultRedisConfig {

     }
     ```

   - WebMvcConfig 

     ```java
     /**
      * WebMvc配置
      * 全局过滤器，验证标识头
      */
     @Configuration
     public class WebMvcConfig extends DefaultWebMvcConfig {

     }
     ```

##### CRUD使用方法

1. 使用mybatis-plus自动生成工具生成Java类

2. 新建DTO，继承BaseDTO

   ```java
   @Data
   public class DictionaryDTO extends BaseDTO {
       /**
        * 父ID
        */
       @NotBlank(operation = {Operation.SAVE})
       @Unique
       private Long pid;
     
   	...
         
       /**
        * 排序
        */
       @NotBlank(operation = {Operation.SAVE})
       private Integer sort;

       @Override
       public Dictionary buildEntity() {
           Dictionary entity = new Dictionary();
           BeanUtils.copyProperties(this, entity);
           return entity;
       }
   }
   ```

3. 新建Query，继承PageQuery

   ```java
   public class DictionaryQuery extends PageQuery {
       // 通过code查询，默认eq
       @Query()
       private String code;
       // 正序排序
       @Query(Keyword.order_asc)
       private String sort;
   }

   ```

4. 新建Controller类，继承BaseController

   ```java
   @RestController
   @RequestMapping("/dictionary")
   public class DictionaryController extends BaseController<DictionaryService, DictionaryDTO, DictionaryQuery> {
     
   }
   ```

至此，DictionaryController已经拥有了BaseController里的全部功能。

- 分页查询 /page

- 根据查询条件查询列表 /list

- 根据ID查询详情 /info/{id}

- 新增 /save

- 修改 /update

- 新增或修改 /saveOrUpdate

- 删除 /delete

- 导出excel /exportExcel

  导出excel需要重写initExport方法

  ```java
  @Override
  protected void initExport() {
    this.exportTitle = new String[]{"编码","值"}; //导出excel文件名
    this.excelFileName = "字典表.xlsx";// 导出excel文件名
    this.sheetName = "sheet1";// 导出表名 可不赋值
  }
  ```

##### 注解的使用

###### @NotBlank

> Post接口（例如新增、修改、删除）时，用于请求参数非空校验。
>
> 例如，@NotBlank(operation = {Operation.SAVE,Operation.UPDATE})，代表在SAVE和UPDATE的场景下，被此注解修饰的属性必传。需要手动触发：ValidUtil.validSave(dto); ValidUtil.validUpdate(dto);
>
> ```java
> @NotBlank(operation = {Operation.SAVE,Operation.UPDATE})
> private Long pid;
>
> ValidUtil.validSave(dto);
> ValidUtil.validUPdate(dto);
> ```
>
> 也可以自定义方法校验，@NotBlank(custom = "customMethodName")，手动触发：ValidUtil.validCustom(dto,"customMethodName");
>
> ```java
> @NotBlank(custom = "customMethodName")
> private Long pid;
>
> ValidUtil.validCustom(dto,"customMethodName");
> ```

###### @Query

> 配合BaseController的 /page 和 /list 方法，使用此注解修饰的属性，代表需要根据此属性进行筛选查找。
>
> ```java
> public class DictionaryQuery extends PageQuery {
>     // 通过code查询，默认eq
>     @Query()
>     private String code;
>     // 正序排序
>     @Query(Keyword.order_asc)
>     private String sort;
> }
> public enum Keyword {
>     in,
>     eq,
>     like,
>     left_like,
>     right_like,
>     gt,
>     lt,
>     ge,
>     le,
>     not_in,
>     is_not_null,
>     is_null,
>     order_asc,
>     order_desc;
> }
> ```
>
> 

###### @Unique

> 使用BaseController的SAVE方法时，如果需要做唯一校验，在DTO上使用此注解修饰属性，则代表该属性在数据库中不可重复。
>
> 如果去重逻辑比较复杂，则重写uniqueSave方法。
>
> ```java
> @Override
> protected void uniqueSave(DictionaryDTO dto) {
>   // 重复校验逻辑
> }
> ```