package ru.test.dictionaries;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.test.dictionaries.dao.DictionaryDao;
import ru.test.dictionaries.dao.HibernateDictionaryDao;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
public class DictionaryConfig {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryConfig.class);
//    @Autowired
//    DataSource dataSource;
//    @Autowired
//    SessionFactory sessionFactory;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/dictionary?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&UseSSL=false",
                "root", "root");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }


    @Bean
    public HibernateTransactionManager transactionManager() throws IOException {

        HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory());
        txManager.afterPropertiesSet();
        logger.info("Hibernate Transaction Manager init");
        return txManager;
    }


    @Bean
    public DictionaryDao dictionaryDao() throws IOException {
        return new HibernateDictionaryDao(sessionFactory());
    }
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        HibernateTransactionManager manager = new HibernateTransactionManager(sessionFactory);
//        manager.setDataSource(dataSource);
//
//        return manager;
//    }
}
