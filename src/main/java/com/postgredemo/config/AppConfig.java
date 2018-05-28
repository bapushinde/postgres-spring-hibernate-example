package com.postgredemo.config;

import com.postgredemo.entity.BaseEntity;
import com.postgredemo.entity.User;
import com.postgredemo.entity.UserInfo;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = {
    @ComponentScan("com.postgredemo.dao"),
    @ComponentScan("com.postgredemo.service"),
    @ComponentScan("com.vladmihalcea.hibernate.type.json")
})
@Import(com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
public class AppConfig {

  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

  @Autowired
  private Environment env;

  @Autowired
  private JsonBinaryType jsonBinaryType;

  @Bean
  public DataSource getDataSource() {
    LOG.info("Loading data source");
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(env.getProperty("db.driver"));
    dataSource.setUrl(env.getProperty("db.url"));
    dataSource.setUsername(env.getProperty("db.username"));
    dataSource.setPassword(env.getProperty("db.password"));
    LOG.info("Data source loaded");

    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean getSessionFactory() {
    LOG.info("Initializing bean factory ");
    LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
    factoryBean.setDataSource(getDataSource());

    Properties props = new Properties();
    props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

    factoryBean.setAnnotatedPackages("com.vladmihalcea.hibernate.type.json");
    factoryBean.setHibernateProperties(props);
    factoryBean.setAnnotatedClasses(BaseEntity.class, User.class);

    LOG.info("Bean factory initialised");

    return factoryBean;
  }

  @Bean
  public HibernateTransactionManager getTransactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(getSessionFactory().getObject());
    return transactionManager;
  }
}