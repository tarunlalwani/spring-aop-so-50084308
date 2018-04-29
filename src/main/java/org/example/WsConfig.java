package org.example;

import java.util.List;

import org.aspect.PersistentAspect;
import org.aspectj.lang.Aspects;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.annotation.PostConstruct;

@EnableWs
@Configuration
@EnableAspectJAutoProxy
public class WsConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/v1/*");
    }

    @Bean
    public XsdSchema schema() {
        return new SimpleXsdSchema(new ClassPathResource("country.xsd"));
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        String[] jaxbContext = new String[] { "io.spring.guides.gs_producing_web_service" };
        marshaller.setContextPaths(jaxbContext);
        return marshaller;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        // aop not working
//        persistentAspect();
        AppConfig config = config();
//        System.out.println("New is config aop proxy: " + AopUtils.isAopProxy(config));
//
//        config.setText("tarun lalwani");
//        config.setText("Tarun Lalwani");
//        interceptors.add(new CustomValidatingInterceptor(schema(), config()));
        // aop working
        System.out.println("Loading addInterceptors");
        interceptors.add(new CustomValidatingInterceptor(schema(), null));
    }

    @Bean

    public AppConfig config() {
        System.out.println("Loading config Bean");
        AppConfig config = null;
        config = new AppConfig();

        System.out.println("1 is config aop proxy: " + AopUtils.isAopProxy(config));
        System.out.println("1 is config CGILIB proxy: " + AopUtils.isCglibProxy(config));

        return config;
    }


    @Bean
    public PersistentAspect persistentAspect() {
        System.out.println("Loading persistentAspect Bean");
        PersistentAspect persistentAspect = new PersistentAspect();

        return persistentAspect;
    }


    @Bean
    public Object testAop(AppConfig config) {
        System.out.println("2 is config aop proxy: " + AopUtils.isAopProxy(config));
        System.out.println("2 is config CGILIB proxy: " + AopUtils.isCglibProxy(config));

        return config;
    }
}
