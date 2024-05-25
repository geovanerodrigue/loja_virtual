package com.loja.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja.model.Usuario;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	//configurando o gerenciandor de autenticacao
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

	   //obriga a autenicar a aurl
       super(new AntPathRequestMatcher(url));

       //gerenciador de autenticacao
       setAuthenticationManager(authenticationManager);

	}


	//retorna o usuario ao processar a authenticacao
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		//obert usuario
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

		//retorna o user com loginne senha
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		try {
			new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
