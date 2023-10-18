package com.vigulear.spring.mvc_hibernate_aop.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "com.vigulear.spring.mvc_hibernate_aop")
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SpringConfig {

  private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
  private static final String JDBC_URL =
      "jdbc:oracle:thin:@//localhost:1521/XEPDB1?useSSL=false&amp;serverTimezone=UTC";
  private static final String USER = "crme059";
  private static final String PASSWORD = "root";

  @Bean
  public DataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(DRIVER_CLASS);
      dataSource.setJdbcUrl(JDBC_URL);
      dataSource.setUser(USER);
      dataSource.setPassword(PASSWORD);
    } catch (PropertyVetoException e) {
      throw new RuntimeException(e);
    }
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.vigulear.spring.mvc_hibernate_aop");

    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
    hibernateProperties.setProperty("hibernate.show_sql", "true");
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

    sessionFactory.setHibernateProperties(hibernateProperties);

    return sessionFactory;
  }

  @Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();

    transactionManager.setSessionFactory(sessionFactory().getObject());

    return transactionManager;
  }

  @Bean
  public ViewResolver configureViewResolver() {
    InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
    viewResolve.setPrefix("/WEB-INF/view/");
    viewResolve.setSuffix(".jsp");

    return viewResolve;
  }
}
