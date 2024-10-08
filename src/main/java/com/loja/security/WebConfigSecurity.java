package com.loja.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.loja.service.ImplementecaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	@Autowired
	private ImplementecaoUserDetailsService implementecaoUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.POST, "/requisicaojunoboleto/**", "/notificacaoapiv2").permitAll()
		.antMatchers(HttpMethod.GET, "/requisicaojunoboleto/**", "/notificacaoapiv2").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

		//redireciona ou da  um retorno para index quando desloga
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

		//mapeia o logout do sistema
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

		//filtra as requisicoes para logout de JWT
		.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)

		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	//consulta o user no banco com security
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(implementecaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	//ignora algumas url livre de autenticacao
	@Override
	public void configure(WebSecurity web) throws Exception {
      web.ignoring()
      .antMatchers(HttpMethod.GET, "/requisicaojunoboleto/**")
      .antMatchers(HttpMethod.POST, "/requisicaojunoboleto/**");
	}

}
