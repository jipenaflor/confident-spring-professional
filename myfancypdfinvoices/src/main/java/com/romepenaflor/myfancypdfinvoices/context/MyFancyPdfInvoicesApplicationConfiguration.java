package com.romepenaflor.myfancypdfinvoices.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romepenaflor.myfancypdfinvoices.ApplicationLauncher;
import com.romepenaflor.myfancypdfinvoices.service.InvoiceService;
import com.romepenaflor.myfancypdfinvoices.service.UserService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/*
@PropertySource reads in the properties files. When there are multiples, the bottom one
has the precedence, rewriting the ones on top.
*/

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties"
                , ignoreResourceNotFound = true)
public class MyFancyPdfInvoicesApplicationConfiguration {
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

    // ObjectMapper bean remains because it is from Jackson, a third-party library
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
