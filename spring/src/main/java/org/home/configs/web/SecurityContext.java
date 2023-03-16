package org.home.configs.web;

import org.home.components.scopes.web.CustomAuthenticationProvider;
import org.home.components.scopes.web.CustomUserDetailsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
// возможен параметр debug=true, но рекомендуется только для разработки - поток логов зашкаливает
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityContext extends WebSecurityConfigurerAdapter {

	// конфигурация web based security для конкретных http-запросов
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll();
		http.formLogin().loginPage("/signin");
		http.logout().invalidateHttpSession(true).logoutSuccessUrl("/").logoutUrl("/signout");
		http.anonymous().authorities("USER_ANONYMOUS").principal("guest").key("foobar");
	}

	// настройка фильтра запросов
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/javascript/share/**");
	}

	// установка провайдера авторизации (может быть примитивная - InMemory, или на основе токенов, связанная с БД и т.д.
	// в данном случае это установка кастомного провайдера
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// требование конфигуратора, без определения менеджера вылетает исключение; базовое поведение
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// бин кастомного провайдера
	@Bean(name = "authenticationProvider")
	public AuthenticationProvider authenticationProvider() {
		return new CustomAuthenticationProvider();
	}

	// бин кастомного UserDetailsService
	@Bean(name = "userDetailsService")
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsManager();
	}

	// кодер для паролей; на смену deprecated org.springframework.security.authentication.encoding.PasswordEncoder
	// относительно недавно появился новый интерфейс org.springframework.security.crypto.password.PasswordEncoder
	// можно использовать BCryptPasswordEncoder на основе хеш-функции BCrypt, или StandartPasswordEncoder, базирующийся
	// на алгоритме SHA-256 или NoOpPasswordEncoder без шифрования пароля (рекомендован для фазы разработки)
	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}