package com.loja.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.loja.model.AccesTokenJunoAPI;

@Service
public class AcessTokenJunoService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public AccesTokenJunoAPI buscaTokenAtivo() {
		
		try {
		AccesTokenJunoAPI accesTokenJunoAPI = (AccesTokenJunoAPI) entityManager
				.createQuery("select a from AccesTokenJunoAPI a ")
				.setMaxResults(1).getSingleResult();
		return accesTokenJunoAPI;
		}catch (NoResultException e) {
			return null;
		}
		

	}

}
