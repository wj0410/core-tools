package com.wj.mybatisplus.generator;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis-plus 自动生成代码工具
 *
 * @author wangjie
 * @version 1.0
 * @date 2022年01月13日10时52分
 */
public class SqlGenerator {
    private static String mysql_driver = "";
    private static String mysql_ip = "";
    private static String mysql_port = "";
    private static String mysql_database = "";
    private static String mysql_user = "";
    private static String mysql_pwd = "";
    private static String project_module = "";
    private static String project_package = "";
    private static String base_entity = "";
    private static String base_controller = "";
    private static String ignore_prefix = "";
    // 需要继承BaseEntity的表
    private static List<String> extBaseEntityTableNames = new ArrayList<>();
    // 不需要继承BaseEntity的表
    private static List<String> notExtBaseEntityTableNames = new ArrayList<>();

    static {
        // MYSQL 配置
        mysql_driver = "com.mysql.cj.jdbc.Driver";
        mysql_ip = "127.0.0.1";
        mysql_port = "3306";
        mysql_database = "auth";
        mysql_user = "root";
        mysql_pwd = "root";
        // 继承实体配置
        base_entity = "com.wj.core.tools.mybatisplus.BaseEntity";
        base_controller = "com.wj.core.tools.restful.controller.BaseController";
        // ========= 以下配置必须修改 =========
        // ******** 项目配置 ********
        project_module = "auth-service";
        project_package = "com.wj.auth";
        // ******** 生成实体忽略前缀 ********
        ignore_prefix = "";
        // ******** 要生成的表 ********
        // ******** 需要继承BaseEntity的表 ********
        extBaseEntityTableNames.add("sys_user");
    }

    public static void main(String[] args) {
        //继承BaseEntity的表。可获取BaseEntity中的公共字段，若表中没有公共字段，则写在第二个数组中
        for (String tableName : extBaseEntityTableNames) {
            generator(tableName, true);
        }
        for (String tableName : notExtBaseEntityTableNames) {
            generator(tableName, false);
        }
    }

    private static void generator(String tableName, boolean isExtBaseEntity) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //生成路径：
        gc.setOutputDir(projectPath + "/" + project_module + "/src/main/java");
        gc.setAuthor("");
        //是否覆盖 默认false
        gc.setFileOverride(true);
        //ActiveRecord特性：直接用实体类去调操作crud的方法，实体会继承model，这与实体继承BaseEntity冲突
        gc.setActiveRecord(false);
        //二级缓存, 如果需要,就用redis在service做缓存，不需要mybatis做缓存
        gc.setEnableCache(false);
        //生成后是否打开所在的文件夹
        gc.setOpen(false);
        //启用swagger2注解。否则就是/** */
        gc.setSwagger2(false);
        //设置生成实体后缀，生成其他类同理。默认规则即可
//        gc.setEntityName("%sEntity");
        //以下两个参数在自动生成mapper.xml时有效
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        gc.setServiceName("%sService");

        //日期使用哪种类型。这里使用java.util.date；默认是java.time
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        //**数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + mysql_ip + ":" + mysql_port + "/" + mysql_database + "?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");
        //设置数据库模式名：部分数据库（如pgsql）有此模式
        //dsc.setSchemaName("base-setting");
        dsc.setDriverName(mysql_driver);
        dsc.setUsername(mysql_user);
        dsc.setPassword(mysql_pwd);
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        //在此主包下生成代码
        pc.setParent(project_package);
        //主包下再设置父包名。上面主包已设置为 com.demo.父包名，这里不需要。
//        pc.setModuleName("");
        //设置生成xml文件的包名，设置其他包同理。默认即可
//        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        //设为null就是不生成。慎用以免覆盖
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig
//                .setEntity(null)
//                .setController(null)
//                .setMapper(null)
//                .setXml(null)
//                .setService(null)
//                .setServiceImpl(null)
        ;
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategy = new StrategyConfig();
        //驼峰映射
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 全局大写命名
        // strategy.setCapitalMode(true);
        //生成实体继承BaseEntity
        if (isExtBaseEntity) {
//			strategy.setSuperControllerClass("org.snebpaas.core.boot.ctrl.PaasController");
//			strategy.setSuperServiceClass("org.snebpaas.core.mp.base.BaseService");
//			strategy.setSuperServiceImplClass("org.snebpaas.core.mp.base.BaseServiceImpl");
//			strategy.setSuperMapperClass("org.snebpaas.core.mp.mapper.PaasMapper");
            //指定BaseEntity包名路径,统一使用此baseEntity
            strategy.setSuperEntityClass(base_entity);
            //指定继承BaseEntity那些字段
            strategy.setSuperEntityColumns("create_by", "create_time", "update_by", "update_time", "is_delete");
        }
        //生成的实体使用lomlok
        strategy.setEntityLombokModel(true);
        //生成的controlle继承BaseController需要指定包名路径
        strategy.setSuperControllerClass(base_controller);
        //生成的Controller中表名映射到url使用"-"连接
        strategy.setControllerMappingHyphenStyle(true);
        //生成的controller使用@RestController注解
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableName);

        //实体生成注解
        strategy.setEntityTableFieldAnnotationEnable(true);


        //生成的entity 等去除前缀
        strategy.setTablePrefix(ignore_prefix);
        mpg.setStrategy(strategy);
        mpg.execute();
        System.out.println("====> 生成代码成功！");
    }
}
