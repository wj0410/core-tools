#### core-tools使用教程

1. pom引入core-tools包。

   > 两种方式
   >
   > 1. 将jar打包到本地maven仓库
   >
   >    ```Xml
   >    1.前往到jar包所在目录，执行以下代码
   >    mvn install:install-file "-Dfile=./core-tools-1.0.1.jar" "-DgroupId=io.github.wj0410" "-DartifactId=core-tools"  "-Dversion=0.0.1-SNAPSHOT"  "-Dpackaging=jar"
   >
   >    2.引入pom依赖
   >    <dependency>
   >    	<groupId>io.github.wj0410</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>1.0.1</version>
   >    </dependency>
   >    ```
   >
   > 2. 从本地文件引入
   >
   >    ```xml
   >    1.将jar包放到项目目录
   >    2.引入pom依赖
   >    <dependency>
   >    	<groupId>io.github.wj0410</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>1.0.1</version>
   >    	<scope>system</scope>
   >    	<systemPath>${project.basedir}/lib/core-tools-1.0.1.jar</systemPath>
   >    </dependency>
   >    ```
   >
   > 3. 从maven中央仓库引入
   >
   >    ```xml
   >    <dependency>
   >    	<groupId>io.github.wj0410</groupId>
   >    	<artifactId>core-tools</artifactId>
   >    	<version>1.0.1</version>
   >    </dependency>
   >    ```
   >
   >    可能会依赖poi
   >
   >    ```xml
   >    <dependency>
   >        <groupId>org.apache.poi</groupId>
   >        <artifactId>poi</artifactId>
   >        <version>5.0.0</version>
   >    </dependency>
   >    ```
   >
   >    ​

2. 在Application启动类里按需注册Bean对象

   ```java
   // mybatis-plus 字段填充控制器，实现公共字段自动写入
   @Bean
   public MetaObjectHandler defaultMetaObjectHandler() {
     return new DefaultMetaObjectHandler();
   }
   ```

3. 按需编写配置类

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

   - WebMvcConfig 

     ```java
     /**
      * WebMvc配置
      * 全局过滤器，验证标识头，让服务只能从网关调用
      * 可自行修改
      * wj.auth.skip-gateway-urls 配置项，可以直接通过服务访问，不需要经过网关的url
      */
     @Configuration
     public class WebMvcConfig extends AuthSecretKeyWebMvcConfig {

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

- 批量新增 /saveBatch

- 修改 /update

- 新增或修改 /saveOrUpdate

- 删除 /delete

- 导出excel /exportExcel

  导出excel需要重写initExport方法

  ```java
  @Override
  protected void initExport() {
    this.exportTitle = new String[]{"编码:code:50","值:value:50"}; //表头 中文名：属性名：单元格宽度
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
>     @Query({Keyword.eq,Keyword.order_asc})
>   	private String status;
>     // 正序排序
>     @Query(Keyword.order_asc)
>     private String sort;
>     
> }
> public enum Keyword {
>     eq,
>     like,
>     left_like,
>     right_like,
>     gt,
>     lt,
>     ge,
>     le,
>     in,
>     not_in,
>     is_not_null,
>     is_null,
>     order_asc,
>     order_desc;
> }
> ```
>
> 默认按照属性名的驼峰去查找，如果需要自定义字段名，则需要加上column属性。
>
> ```java
> @Query(column="custom_column")
> private String custom;
> ```
>
> 

###### @Unique

> 使用BaseController的SAVE方法时，如果需要做唯一校验，在DTO上使用此注解修饰属性，则代表该属性在数据库中不可重复。
>
> ```java
> @Unique(tip="用户id:${value} 已存在!")
> private String userId;
> ```
>
> 如果去重逻辑比较复杂，则重写uniqueSave方法。
>
> ```java
> @Override
> protected void uniqueSave(DictionaryDTO dto) {
>   // 重复校验逻辑
> }
> ```



# 更新日志

- 1.0.2	2022-11-01

  > 更新内容
  >
  > 1. @Query 注解支持同时多种查询

  - 1.0.32022-11-04

  > 更新内容
  >
  > 1. 新增了批量保存方法
  > 2. @Unique注解增加了tip属性 ${value}可以用来代替该属性的值