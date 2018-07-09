package me.jmll.utm.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import me.jmll.utm.filter.Authorization;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;

public class InitializeApp implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext container) throws ServletException
    {
    	
        container.getServletRegistration("default").addMapping("/resource/*");
        /**
         * Crea e inicializa Spring Root Context
         **/
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootContextConfig.class);
        /**
         *  Administra el ciclo de vida del Root Application Context
         **/
        container.addListener(new ContextLoaderListener(rootContext));
        /**
         * Crea e inicializa Spring Servlet Context
         **/
        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.register(ServletContextConfig.class);
        
        /**
         * Inicializando Servlet Dispatcher
         **/
        ServletRegistration.Dynamic dispatcher = container.addServlet("springDispatcher", new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        
        /**
         *  Configura Multipart para springDispatcher
         *  javax.servlet.MultipartConfigElement
         *  location -> null
         *  maxFileSize = 20MB
         *  maxRequestSize = 40MB
         *  fileSizeThreshold = 512000
         * */
        dispatcher.setMultipartConfig(new MultipartConfigElement(
                null, 20_971_520L, 41_943_040L, 512_000
        ));
        
        /**
         * Crea e inicializa Spring REST Servlet Context
         **/
        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        restContext.register(RestServletContextConfig.class);
        DispatcherServlet servlet = new DispatcherServlet(restContext);
        servlet.setDispatchOptionsRequest(true);
        dispatcher = container.addServlet("springRestDispatcher", servlet);
        dispatcher.setLoadOnStartup(2);
        dispatcher.addMapping("/api/v1/*");
        
        /**
         * Registra Filters para autenticación me.jmll.utm.filter.Authorization
         * para los url patterns /dashboard, /list, /upload y sus variantes con expresiones ant
         * */
        FilterRegistration.Dynamic registration = container.addFilter("authorizationFilter", new Authorization());
        registration.addMappingForUrlPatterns(null, false, "/dashboard", "/dashboard/*",
        		"/list", "/list/*", "/upload", "/upload/*");
    }
}
