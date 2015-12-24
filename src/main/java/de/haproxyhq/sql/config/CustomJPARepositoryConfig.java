/**
 * 
 */
package de.haproxyhq.sql.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.haproxyhq.utils.PackageUtils;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { PackageUtils.SQL_REPOSITORY_PACKAGE })
public class CustomJPARepositoryConfig {
	
	@Value("${database.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	
	@Value("${database.show_sql")
	private String hibernateShowSql;
	
	@Value("${database.driverClassName")
	private String driverClassName;
	
	@Autowired
	private DataSource dataSource;
	
	@Configuration
	@Profile("default")
	static class Default {
		
		@Value("${database.url}")
		private String databaseUrl;
		
		@Value("${database.user}")
	    private String databaseUser;
		
		@Value("${database.password}")
	    private String databasePassword;
		
		@Value("${database.driver}")
		private String databaseDriver;
		
		@Bean
		public static PropertyPlaceholderConfigurer jpaPropertyPlaceholderConfigurer() {
			PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			propertyPlaceholderConfigurer.setLocation(new ClassPathResource("application-model-sql.properties"));
			propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
			return propertyPlaceholderConfigurer;
		}
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
			driverManagerDataSource.setDriverClassName(databaseDriver);
			driverManagerDataSource.setUrl(databaseUrl);
			driverManagerDataSource.setUsername(databaseUser);
			driverManagerDataSource.setPassword(databasePassword);			
			return driverManagerDataSource;
		}
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.parseBoolean(hbm2ddlAuto));
		vendorAdapter.setShowSql(Boolean.parseBoolean(hibernateShowSql));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(PackageUtils.SQL_PACKAGE);
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();
		
		return factory.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
		
}