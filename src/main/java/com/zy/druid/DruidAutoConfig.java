package com.zy.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.zy.conf.PagePlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by zy on 2017/3/28.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@ConditionalOnClass(DruidDataSource.class)
//@ConditionalOnProperty(prefix = "spring.druid", name = "url")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DruidAutoConfig {

    @Bean
    public DruidProperties getDruidPo(){
        return new DruidProperties();
    }
    @Autowired
    private DruidProperties druidProperties;

    @Bean
    public DataSource dataSource() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());
        if (druidProperties.getInitialSize() > 0) {
            dataSource.setInitialSize(druidProperties.getInitialSize());
        }
        if (druidProperties.getMinIdle() > 0) {
            dataSource.setMinIdle(druidProperties.getMinIdle());
        }
        if (druidProperties.getMaxActive() > 0) {
            dataSource.setMaxActive(druidProperties.getMaxActive());
        }
        dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
       // sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis.xml"));
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        /*注册sql拦截器*/
        sqlSessionFactory.getConfiguration().addInterceptor(new PagePlugin());
        return sqlSessionFactoryBean.getObject();
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplateBean() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean());
        return sqlSessionTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }




}
