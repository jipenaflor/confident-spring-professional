package com.romepenaflor.myfancypdfinvoices.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romepenaflor.myfancypdfinvoices.ApplicationLauncher;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;

/*
@PropertySource reads in the properties files. When there are multiples, the bottom one
has the precedence, rewriting the ones on top.
@EnableWebMvc initializes Spring Web MVC with default config, and automatically registers a
JSON converter for JSON <-> Java object conversions
*/

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties"
                , ignoreResourceNotFound = true)
@EnableWebMvc
public class ApplicationConfiguration {
    // by default, @ComponentScan only scans the packages and subpackages of the annotated class
    // hence the addition of a class in root packaged as basePackageClass

    /*
    @Bean tells the applicationContext to create an instance of the service in startup.
    By default, @Bean methods are singleton (single instance only) but you can
    use @Scope to change this behavior. With this, new instance are created every call.
    */

    /*
    // removed since they are now replaced with @Component
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserService userService() {
        return new UserService();
    }
    @Bean
    public InvoiceService invoiceService() {
        return new InvoiceService(userService());
    }
    */

    // Needed for validation to work in controllers if the annotations are
    // along the @RequestParam parameters
    /*
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    */

    // ObjectMapper bean remains because it is from Jackson, a third-party library
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();   // H2 datasource
        /*
        Open a db in a file called myfirsth2database.mv.db in ~/ directory, then
        run the SQL file in the classpath whenever someone connects to the database
        */
        ds.setURL("jdbc:h2:~/myFirstH2Database;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

    // wrapper class around Java's JDBC facilities to execute SQL statements
    // thread-safe, can be used by multiple threads at the same time
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    // Beans needed for Thymeleaf and Spring MVC to work together
    // Spring asks ViewResolvers to find and render the index.html template
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());

        viewResolver.setOrder(1); // optional
        viewResolver.setViewNames(new String[] {"*.html", "*.xhtml"}); // optional
        return viewResolver;
    }

    // Configured to work for a Thymeleaf-specific configuration bean
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    // Class that finds your thymeleaf template
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
