package com.loja.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.ExceptionMentoriaJava;
import com.loja.model.Endereco;
import com.loja.model.PessoaFisica;
import com.loja.model.PessoaJuridica;
import com.loja.model.dto.CepDto;
import com.loja.repository.EnderecoRepository;
import com.loja.repository.PessoaRepository;
import com.loja.service.PessoaUserService;
import com.loja.util.ValidaCPF;
import com.loja.util.ValidaCnpj;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDto> consultaCep(@PathVariable("cep") String cep) {
		
		return new ResponseEntity<CepDto>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{
		
		if(pessoaJuridica ==  null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não pode sere NULL");
		}
		
		if(pessoaJuridica.getId() ==  null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		
		if(pessoaJuridica.getId() ==  null && pessoaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe Inscrição Estadual cadastrado com o número: " + pessoaJuridica.getInscEstadual());
		}
		
		if(!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("Cnpj: " + pessoaJuridica.getCnpj() + " está inválido.");
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
		    for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
		    	
				CepDto cepDto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDto.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDto.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDto.getUf());
			}
		} else {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if(!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					
					CepDto cepDto = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
					
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDto.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDto.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDto.getUf());
					
				}
				
			}
		}
			
		pessoaJuridica = pessoaUserService.salvarPessoajuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{
		
		if(pessoaFisica ==  null) {
			throw new ExceptionMentoriaJava("Pessoa fisica não pode sere NULL");
		}
		
		if(pessoaFisica.getId() ==  null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}
		
		if(!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("Cnpj: " + pessoaFisica.getCpf() + " está inválido.");
		}
			
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
		
	}

}
