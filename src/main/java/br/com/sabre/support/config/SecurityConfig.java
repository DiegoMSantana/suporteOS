package br.com.sabre.support.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.sabre.support.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	// Autentica o usuário pelo banco de dados
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	// Ignora CSS e Javascript da aplicação
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layout/**").antMatchers("/images/**");

	}

	// Implementa autenticação
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * CharacterEncodingFilter filter = new CharacterEncodingFilter();
		 * filter.setEncoding("UTF-8"); filter.setForceEncoding(true);
		 * http.addFilterBefore(filter, CsrfFilter.class);
		 */

		http.authorizeRequests()
				// .antMatchers("/cidades/nova").hasAuthority("CADASTRAR_CIDADE")
				// .antMatchers("/usuarios/**").hasAuthority("CADASTRAR_USUARIO")
				// .antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")
				.antMatchers("/cliente/**").hasRole("CADASTRAR_USUARIO").anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().rememberMe().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).and().exceptionHandling()
				.accessDeniedPage("/403").and().sessionManagement().invalidSessionUrl("/login").maximumSessions(1)
				.expiredUrl("/login");

		// .and()
		// .csrf().disable();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
