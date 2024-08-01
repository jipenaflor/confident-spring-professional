package com.romepenaflor.myfancypdfinvoices;

import com.romepenaflor.myfancypdfinvoices.web.MyFirstServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

public class ApplicationLauncher {

    public static void main(String[] args) throws LifecycleException {
        // connect to http://localhost:8080
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        // "" as contextPath means we only have one application
        // null static files since we have no static files to deliver
        Context ctx = tomcat.addContext("", null);

        // add servlet to tomcat
        Wrapper servlet = Tomcat.addServlet(ctx, "myFirstServlet", new MyFirstServlet());

        // 1 to load servlet on the very first HTTP request
        servlet.setLoadOnStartup(1);

        // servlet to response to requests starting with "/"
        servlet.addMapping("/*");

        tomcat.start();
    }
}
