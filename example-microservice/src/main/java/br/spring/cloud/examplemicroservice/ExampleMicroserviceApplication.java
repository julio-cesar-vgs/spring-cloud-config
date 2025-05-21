package br.spring.cloud.examplemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class, // Exclui a autoconfiguração de DataSource
        HibernateJpaAutoConfiguration.class // Exclui a autoconfiguração de JPA/Hibernate
})
public class ExampleMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleMicroserviceApplication.class, args);
    }

}
