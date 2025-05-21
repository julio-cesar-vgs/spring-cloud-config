package br.spring.cloud.examplemicroservice.configuration;

import br.spring.cloud.examplemicroservice.routing.RoutingDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name="primaryDataSource")
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="secondaryDataSource")
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEmf(
            EntityManagerFactoryBuilder b,
            @Qualifier("primaryDataSource") DataSource ds) {
        return b.dataSource(ds)
                .packages("br.spring.cloud.examplemicroservice")
                .persistenceUnit("primary")
                .build();
    }

    @Bean(name="secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEmf(
            EntityManagerFactoryBuilder b,
            @Qualifier("secondaryDataSource") DataSource ds) {
        return b.dataSource(ds)
                .packages("br.spring.cloud.examplemicroservice")
                .persistenceUnit("secondary")
                .build();
    }

    @Primary
    @Bean(name="primaryTransactionManager")
    public PlatformTransactionManager primaryTx(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name="secondaryTransactionManager")
    public PlatformTransactionManager secondaryTx(
            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("primaryDataSource") DataSource primary,
            @Qualifier("secondaryDataSource") DataSource secondary) {
        RoutingDataSource routing = new RoutingDataSource();
        Map<Object,Object> map = Map.of("primary", primary, "secondary", secondary);
        routing.setTargetDataSources(map);
        routing.setDefaultTargetDataSource(primary);
        return routing;
    }

}
