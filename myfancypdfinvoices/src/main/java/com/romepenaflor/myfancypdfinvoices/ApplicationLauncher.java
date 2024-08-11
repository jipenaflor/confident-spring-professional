package com.romepenaflor.myfancypdfinvoices;

import com.romepenaflor.myfancypdfinvoices.context.ApplicationConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {
        // connect to http://localhost:8080
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        // "" as contextPath means we only have one application
        // null static files since we have no static files to deliver
        Context tomcatCtx = tomcat.addContext("", null);

        /*
        //add servlet to tomcat
        Wrapper servlet = Tomcat.addServlet(ctx, "myFirstServlet", new MyFirstServlet());

        // 1 to load servlet on the very first HTTP request
        servlet.setLoadOnStartup(1);

        // servlet to response to requests starting with "/"
        servlet.addMapping("/*");
        */

        /*
         Creating a DispatcherServlet, Spring MVC's own servlet to replace the servlet above
         and to recognize @Controller, @GetMapping, etc...
        */
        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());

        // entry point of WebMVC
        // able to forward incoming request to controllers and back to browser
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);

        // register dispatcherServlet
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }
}
