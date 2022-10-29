package com.wj.workflow.conf;

import com.wj.core.tools.config.DefaultMybatisPlusConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
