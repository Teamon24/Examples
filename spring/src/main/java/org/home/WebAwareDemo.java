package org.home;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.home.configs.web.ApplicationContext;
import org.home.configs.web.WebContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Properties;

public class WebAwareDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        try (InputStream stream = WebAwareDemo.class.getResourceAsStream("/WEB-INF/application.properties")) {
            properties.load(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // то самое непосредственное указание логгеру log4j на получение параметров из конфигурации
        PropertyConfigurator.configure(properties);

        AnnotationConfigWebApplicationContext springWebAppContext = springWebAppContext(properties);

        WebAppContext jettyWebAppContext = jettyWebAppContext(
                properties,
                springWebAppContext,
                dispatcherServletHolder(springWebAppContext),
                securityFilterChain()
            );

        startJetty(properties, jettyWebAppContext);
    }

    /**
     * @return веб-контекст на базе нашей Java-based конфигурации WebContext.
     */
    private static AnnotationConfigWebApplicationContext springWebAppContext(Properties properties) {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebContext.class);

        // заполняем окружение контекста параметрами из файла конфигурации проекта
        webContext
            .getEnvironment()
            .getPropertySources()
            .addLast(
                new PropertiesPropertySource("applicationEnvironment", properties)
            );

        return webContext;
    }

    /**
     * @return стандартный сервлет Spring MVC.
     */
    private static ServletHolder dispatcherServletHolder(AnnotationConfigWebApplicationContext webContext) {
        ServletHolder servletHolder = new ServletHolder("test-dispatcher", new DispatcherServlet(webContext));
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitOrder(1);
        return servletHolder;
    }

    /**
     * @return стандартный фильтр Spring Security.
     */
    private static FilterHolder securityFilterChain() {
        FilterHolder filterHolder = new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain"));
        filterHolder.setAsyncSupported(true);
        return filterHolder;
    }

    /**
     * @return веб-контекст Jetty
     */
    private static WebAppContext jettyWebAppContext(
        Properties properties,
        AnnotationConfigWebApplicationContext webApplicationContext,
        ServletHolder servletHolder,
        FilterHolder filterHolder
    ) {
        WebAppContext jettyWebAppContext = new WebAppContext();
        // указываем класс контекста приложения
        jettyWebAppContext.setInitParameter("contextConfigLocation", ApplicationContext.class.getName());
        // базовая папка проекта, где находится WEB-INF
        jettyWebAppContext.setResourceBase("resource");
        // назначаем стандартного слушателя, Context Path, созданные сервлет и фильтр
        jettyWebAppContext.addEventListener(new ContextLoaderListener(webApplicationContext));
        jettyWebAppContext.setContextPath(properties.getProperty("base.url"));
        jettyWebAppContext.addServlet(servletHolder, "/");
        jettyWebAppContext.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR));
        return jettyWebAppContext;
    }

    private static void startJetty(Properties properties, WebAppContext jettyWebAppContext) throws Exception {
        Server server = new Server(Integer.parseInt(properties.getProperty("base.port")));
        server.setHandler(jettyWebAppContext);
        server.start();
        server.join();
    }
}
