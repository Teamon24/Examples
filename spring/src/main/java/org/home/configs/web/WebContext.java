package org.home.configs.web;

import org.hibernate.SessionFactory;
import org.home.components.scopes.web.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

@EnableWebMvc
@Configuration
@Import(WebAwareConfiguration.class)
public class WebContext implements WebMvcConfigurer {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SessionFactory sessionFactory;

	// стандартный обработчик статических ресурсов, установка order в -1 желательна,
	// иначе в цепочке обработки запроса также будет вызван метод контроллера с маппингом "/**" или другими перекающимися
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/javascript/share/**").addResourceLocations("/javascript/share/");
		registry.setOrder(-1);
	}

	// стандартный resolver представлений, для небольших приложений избыточен, надобности нет
	// использован конечный класс InternalResourceViewResolver, но разницы в работе с UrlBasedViewResolver не замечено
	// использование JstlView позволяет делать JSTL-инъекции в динамические страницы или фрагменты страниц
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	// очень важное переопределение метода, что позволяет использовать дефолтный сервлет веб-сервера,
	// о необходимости которого было сказано в начале публикации
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// валидатор, используемый в проекте для валидации моделей и обработки исключений
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource);
		return validatorFactoryBean;
	}

	// определение валидатора в цепочке прохождения запроса в Spring MVC
	@Override
	public Validator getValidator() {
		return validator();
	}

	// бин конфигурации, расширяющий возможности работы с локализацией (на практике просто сохраняет
	// и читает куку с дефолтной для пользователя локалью)
	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.forLanguageTag("en"));
		return localeResolver;
	}

	// набор интерцептеров, используемых в проекте - можно отказаться совсем или расширить своими
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// интерцептор для определения локали при ее смене пользователем
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		// обычный OSIV интерцептор
		OpenSessionInViewInterceptor openSessionInViewInterceptor = new OpenSessionInViewInterceptor();
		openSessionInViewInterceptor.setSessionFactory(sessionFactory);
		registry.addWebRequestInterceptor(openSessionInViewInterceptor);
		// пользовательский интерцептор для шаблонных запросов
		ApiInterceptor apiInterceptor = new ApiInterceptor();
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
	}
}