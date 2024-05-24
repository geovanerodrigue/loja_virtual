package com.loja.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//cria a autenticacao e retorna a autenticacao JWT
@Service
@Component
public class JWTTokenAutenticacaoService {
	
	//token de validade de 11 dias
	private static final long EXPIRATION_TIME = 959990000;
	
	//chave de senha para juntar com o jwt
	private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	//gera o token e da a resposta para o cliente com JWT
	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		
		//montagem do token
		
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		String token = TOKEN_PREFIX + " " + JWT;
		
		//retorna para a tela e para o cliente outra api, navegador, aplicativo javascrip etc
		response.addHeader(HEADER_STRING, token);
		
		//usado para ver no postman para teste
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
		
	}

}
