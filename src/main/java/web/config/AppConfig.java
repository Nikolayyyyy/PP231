package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableJpaRepositories("web.dao")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {

   @Autowired
   private Environment env;



   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
      dataSource.setUrl(env.getProperty("jdbc.url"));
      dataSource.setUsername(env.getProperty("jdbc.username"));
      dataSource.setPassword(env.getProperty("jdbc.password"));
      return dataSource;
   }
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan(env.getRequiredProperty("jdbc.entity.package"));
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      em.setJpaProperties(getHibernateProperties());
      return em;
   }

   @Bean
   public PlatformTransactionManager platformTransactionManager(){
      JpaTransactionManager jpaTransact = new JpaTransactionManager();
      jpaTransact.setEntityManagerFactory(entityManagerFactoryBean().getObject());

      return jpaTransact;
   }

   private Properties getHibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
      properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
      return properties;
   }

//   private Properties getHisernateProperties() {
//      try {
//      Properties properties = new Properties();
//      InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
//
//         properties.load(is);
//         return properties;
//      } catch (IOException e) {
//         throw new IllegalArgumentException("Can't find", e);
//      }
//   }
}