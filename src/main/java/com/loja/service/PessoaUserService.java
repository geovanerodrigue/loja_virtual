package com.loja.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.loja.model.PessoaFisica;
import com.loja.model.PessoaJuridica;
import com.loja.model.Usuario;
import com.loja.model.dto.CepDto;
import com.loja.repository.PessoaFisicaRepository;
import com.loja.repository.PessoaRepository;
import com.loja.repository.UsuarioRepository;

@Service
public class PessoaUserService {
	
	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	public PessoaJuridica salvarPessoajuridica(PessoaJuridica juridica) {
		
		//juridica = pessoaRepository.save(juridica);
		
		for (int i =0; i < juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
			juridica.getEnderecos().get(i).setEmpresa(juridica);
		}
		
		juridica = pessoaRepository.save(juridica);
		
		Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());
		
		if(usuarioPj ==  null) {
			
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if(constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}
			
			usuarioPj =  new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());
			
			//String senha = "123");
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCript);
			
			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUser(usuarioPj.getId());
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN");
			
			StringBuilder mensagemHtml =  new StringBuilder();
			
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			mensagemHtml.append("<b>Login: </b>"+juridica.getEmail()+"<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			mensagemHtml.append("Obrigado!!");
			
			try {
			//fazer o envio de email do login e da senha
			serviceSendEmail.enviarEmailHtml("Acesso gerado para Loja Virtual", mensagemHtml.toString(), juridica.getEmail());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return  juridica;
		
	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
		//juridica = pesssoaRepository.save(juridica);
			
			for (int i = 0; i< pessoaFisica.getEnderecos().size(); i++) {
				pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
				//pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);
			}
			
			pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
			
			Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
			
			if (usuarioPj == null) {
				
				String constraint = usuarioRepository.consultaConstraintAcesso();
				if (constraint != null) {
					jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
				}
				
				usuarioPj = new Usuario();
				usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
				usuarioPj.setEmpresa(pessoaFisica.getEmpresa());
				usuarioPj.setPessoa(pessoaFisica);
				usuarioPj.setLogin(pessoaFisica.getEmail());
				
				String senha = "" + Calendar.getInstance().getTimeInMillis();
				String senhaCript = new BCryptPasswordEncoder().encode(senha);
				
				usuarioPj.setSenha(senhaCript);
				
				usuarioPj = usuarioRepository.save(usuarioPj);
				
				usuarioRepository.insereAcessoUser(usuarioPj.getId());
				
				StringBuilder menssagemHtml = new StringBuilder();
				
				menssagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
				menssagemHtml.append("<b>Login: </b>"+pessoaFisica.getEmail()+"<br/>");
				menssagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
				menssagemHtml.append("Obrigado!");
				
				try {
				  serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", menssagemHtml.toString() , pessoaFisica.getEmail());
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			return pessoaFisica;
		}
	
	public CepDto consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDto.class).getBody();
	}
	
}
