package com.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	public void atualizaAcessoEndPointPF() {
		jdbcTemplate.execute("begin; update tabela_acesso_end_point set atd_acesso_end_point = atd_acesso_end_point + 1 where nome_end_point = 'END-POINT-NOME-PESSOA-FISICA'; commit;");
		
	}
	

}
