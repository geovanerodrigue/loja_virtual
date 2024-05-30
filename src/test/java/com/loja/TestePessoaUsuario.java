package com.loja;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.loja.controller.PessoaController;
import com.loja.model.PessoaFisica;
import com.loja.model.PessoaJuridica;
import com.loja.repository.PessoaRepository;
import com.loja.service.PessoaUserService;

import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaController pessoaController;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("696969696" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Sauron da Silva");
		pessoaJuridica.setEmail("sauron@gmail.com");
		pessoaJuridica.setTelefone("4012222");
		pessoaJuridica.setInscEstadual("7071707222");
		pessoaJuridica.setInscMunicipal("40414222");
		pessoaJuridica.setNomeFantasia("303130022");
		pessoaJuridica.setRazaoSocial("10110101022");
		
		pessoaController.salvarPj(pessoaJuridica);
		
		//pessoaRepository.save(pessoaJuridica);
		
		/*
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("69696969696");
		pessoaFisica.setNome("Testador Silva");
		pessoaFisica.setEmail("testador@gmail.com");
		pessoaFisica.setTelefone("40028922");
		pessoaFisica.setEmpresa(pessoaFisica);
		*/
		
	}
	
}
