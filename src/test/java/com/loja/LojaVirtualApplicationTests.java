package com.loja;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.loja.controller.AcessoController;
import com.loja.model.Acesso;
import com.loja.repository.AcessoRepository;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests extends TestCase {
	

	@Autowired
    private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Test
	public void testCadastraAcesso() {
		
		Acesso acesso = new Acesso();
		
		
		acesso.setDescricao("ROLE_ADMIN");
		
		assertEquals(true, acesso.getId() == null);
		
		//gravou no banco de dados
		 acesso = acessoController.salvarAcesso(acesso).getBody();
		 
		 assertEquals(true, acesso.getId() > 0);
		 
		 //validar dados salvod da forna correta
		 assertEquals("ROLE_ADMIN", acesso.getDescricao());
		 
		 //teste de carregamento
		 
		 Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		 
		 assertEquals(acesso.getId(), acesso2.getId());
		 
		 //teste de delete
		 acessoRepository.deleteById(acesso2.getId());
		 
		acessoRepository.flush();
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true, acesso3 == null);
		
		//teste de query
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDes("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
	}

}
