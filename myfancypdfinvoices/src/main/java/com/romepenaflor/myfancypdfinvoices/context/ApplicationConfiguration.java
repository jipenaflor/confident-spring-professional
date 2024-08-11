package com.romepenaflor.myfancypdfinvoices.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romepenaflor.myfancypdfinvoices.ApplicationLauncher;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
}
