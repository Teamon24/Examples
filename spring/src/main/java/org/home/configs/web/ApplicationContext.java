package org.home.configs.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ComponentScan(basePackages = "org.home.configs.web")
public class ApplicationContext {

	// загрузка свойств проекта в ConfigurableEnvironment контекста
	// далее будет обсуждение ненужности такого шага в рамках старта embedded Jetty наряду с @PropertySource
	@Bean
	public static PropertySourcesPlaceholderConfigurer appProperty() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	// загрузка локализованных сообщений, в моем случае файлы локализации клиента и обработчика ошибок моделей и исключений
	// при отсутствии таковых необходимости в этом бине нет
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/errors");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}