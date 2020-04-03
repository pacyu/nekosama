package com.nekosamahe.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/index", "/category*", "/archive*", "/about*", "/search*", "/lab*").permitAll()
				.antMatchers("/api/liked/*", "/api/viewed/*", "/api/comment", "/api/feedback").permitAll()
                .antMatchers("/blog/**", "/webjars/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.successHandler(successHandler())
				.defaultSuccessUrl("/manager")
				.failureUrl("/login?error=true")
				.permitAll()
			.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.permitAll();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
		handler.setUseReferer(true);
		return handler;
	}
	
	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user =
	// 		 User.withDefaultPasswordEncoder()
	// 			.username("user")
	// 			.password("password")
	// 			.roles("USER")
	// 			.build();

	// 	return new InMemoryUserDetailsManager(user);
	// }
}