package com.gym.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.gym")
@EnableTransactionManagement
@EnableJpaRepositories("com.gym.repository")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${datasource.name}")
    private String datasourceName;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.format_sql}")
    private String formatSql;

    @Value("${hibernate.import_files}")
    private String importFiles;

    @Value("${h2.console.port}")
    private String h2ConsolePort;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName(datasourceName)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.gym.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(jpaProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public org.h2.tools.Server h2WebServer() throws Exception {
        return org.h2.tools.Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", h2ConsolePort
        );
    }

    private Properties jpaProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        p.setProperty("hibernate.show_sql", showSql);
        p.setProperty("hibernate.format_sql", formatSql);
        p.setProperty("hibernate.hbm2ddl.import_files", importFiles);
        return p;
    }
}
