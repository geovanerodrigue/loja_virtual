package com.loja;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.loja.controller.PessoaController;
import com.loja.enums.TipoEndereco;
import com.loja.model.Endereco;
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
		pessoaJuridica.setTelefone("4012222222");
		pessoaJuridica.setInscEstadual("7071707222");
		pessoaJuridica.setInscMunicipal("40414222222");
		pessoaJuridica.setNomeFantasia("30313002222");
		pessoaJuridica.setRazaoSocial("10110101022222");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("JD dias");
		endereco1.setCep("55656565");
		endereco1.setComplemento("casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("389");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("Av. são joão sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		endereco1.setCidade("Curitiba");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("JD Maracana");
		endereco2.setCep("556564445");
		endereco2.setComplemento("casa azul");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("38");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("Av. são joão quinto");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("RJ");
		endereco2.setCidade("Rio de Janeiro");;
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);
		
		pessoaJuridica =  pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
		//pessoaController.salvarPj(pessoaJuridica);
		
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
