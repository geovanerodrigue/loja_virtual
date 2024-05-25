package com.loja.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.loja.ApplicationContextLoad;
import com.loja.model.Usuario;
import com.loja.repository.UsuarioRepository;

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

	//retorna o usuario validado com token ou caso nao seja valido retorna null
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getHeader(HEADER_STRING);

		if(token != null) {

			String tokenLimpo =  token.replace(TOKEN_PREFIX, "").trim();

			//faz a validacao do token do usuario na requisicao e obten o USER
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(tokenLimpo)
					.getBody().getSubject();

			if(user != null) {

				Usuario usuario = ApplicationContextLoad.getApplicationContext()
						.getBean(UsuarioRepository.class).findUserByLogin(user);

				if(usuario != null) {
				  return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
				}

			}

		}

		liberacaoCors(response);
		return null;
	}

	//fazendo a libveracao contra erro de Cors no navegador
	private void liberacaoCors(HttpServletResponse response) {

		if(response.getHeader("Acces-Control-Allow-Origin") == null) {
			response.addHeader("Acces-Control-Allow-Origin", "*");
		}

		if(response.getHeader("Acces-Control-Allow-Headers") == null) {
			response.addHeader("Acces-Control-Allow-Headers", "*");
		}

		if(response.getHeader("Acces-Control-Request-Headers") == null) {
			response.addHeader("Acces-Control-Request-Headers", "*");
		}

		if(response.getHeader("Acces-Control-Allow-Methods") == null) {
			response.addHeader("Acces-Control-Allow-Methods", "*");
		}

	}

}
