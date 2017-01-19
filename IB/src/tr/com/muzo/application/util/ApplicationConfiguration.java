package tr.com.muzo.application.util;


import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@Configuration
@EnableScheduling
@ComponentScan({ "tr.com.muzo.*" })
@EnableTransactionManagement
public class ApplicationConfiguration{
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		builder.scanPackages("tr.com.muzo.*").addProperties(getHibernateProperties());
		return builder.buildSessionFactory();
	}
	
	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "true");
//		prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		prop.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		prop.put("hibernate.max_fetch_depth", "1");
		prop.put("hibernate.jdbc.batch_size", "30");
		prop.put("hibernate.hbm2ddl.auto", "create");
		prop.put("hibernate.c3p0.min_size", "5");
		prop.put("hibernate.c3p0.max_size", "20");
		prop.put("hibernate.c3p0.timeout", "300");
		prop.put("hibernate.c3p0.max_statements", "50");
		prop.put("hibernate.c3p0.idle_test_period", "3000");
		return prop;
	}

	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setUsername("muzo");
		ds.setPassword("muzo");
//		ds.setDriverClassName("org.postgresql.Driver");
//		ds.setUrl("jdbc:postgresql://localhost:5432/tahmin");
//		ds.setUsername("postgres");
//		ds.setPassword("postgres");
		ds.setInitialSize(3);
		return ds;
	}

	@Bean
	public HibernateTransactionManager txManager() {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(sessionFactory());
		return hibernateTransactionManager;
	}
	
}
