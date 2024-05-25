package com.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loja.model.Usuario;
import com.loja.repository.UsuarioRepository;

@Service
public class ImplementecaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findUserByLogin(username);

		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado.");
		}

		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
	}

}
