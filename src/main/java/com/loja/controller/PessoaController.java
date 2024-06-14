package com.loja.controller;

import java.util.List;

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
import com.loja.enums.TipoPessoa;
import com.loja.model.Endereco;
import com.loja.model.PessoaFisica;
import com.loja.model.PessoaJuridica;
import com.loja.model.dto.CepDto;
import com.loja.model.dto.ConsultaCnpjDto;
import com.loja.repository.EnderecoRepository;
import com.loja.repository.PessoaFisicaRepository;
import com.loja.repository.PessoaRepository;
import com.loja.service.PessoaUserService;
import com.loja.service.ServiceContagemAcessoApi;
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

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private ServiceContagemAcessoApi serviceContagemAcessoApi;


	@ResponseBody
	@GetMapping(value = "**/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){

		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePf(nome.trim().toUpperCase());

		serviceContagemAcessoApi.atualizaAcessoEndPointPF();

		return new ResponseEntity<>(fisicas, HttpStatus.OK);
    }

	@ResponseBody
	@GetMapping(value = "**/consultaPfCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf){

		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorCpf(cpf);

		return new ResponseEntity<>(fisicas, HttpStatus.OK);
    }

	@ResponseBody
	@GetMapping(value = "**/consultaNomePj/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultaNomePj(@PathVariable("nome") String nome){

		List<PessoaJuridica> fisicas = pessoaRepository.pesquisaPorNomePj(nome.trim().toUpperCase());

		return new ResponseEntity<>(fisicas, HttpStatus.OK);
    }

	@ResponseBody
	@GetMapping(value = "**/consultaCnpjPj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaCnpjPj(@PathVariable("cnpj") String cnpj){

		List<PessoaJuridica> fisicas = pessoaRepository.existeCnpjCadastradoList(cnpj);

		return new ResponseEntity<>(fisicas, HttpStatus.OK);
    }


	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDto> consultaCep(@PathVariable("cep") String cep) {

		return new ResponseEntity<>(pessoaUserService.consultaCep(cep), HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/consultaCnpjReceitaWs/{cnpj}")
	public ResponseEntity<ConsultaCnpjDto> consultaCnpjReceitaWs(@PathVariable("cnpj") String cep) {

		return new ResponseEntity<>(pessoaUserService.consultaCnpjReceitaWs(cep), HttpStatus.OK);

	}


	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{

		if(pessoaJuridica ==  null) {
			throw new ExceptionMentoriaJava("Pessoa Juridica não pode ser NULL");
		}

		if(pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoriaJava("Informe o tipo Jurídico ou Fornecedor da loja");
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

		    for (Endereco element : pessoaJuridica.getEnderecos()) {

				CepDto cepDto = pessoaUserService.consultaCep(element.getCep());

				element.setBairro(cepDto.getBairro());
				element.setCidade(cepDto.getLocalidade());
				element.setComplemento(cepDto.getComplemento());
				element.setRuaLogra(cepDto.getLogradouro());
				element.setUf(cepDto.getUf());
			}
		} else {
			for (Endereco element : pessoaJuridica.getEnderecos()) {
				Endereco enderecoTemp = enderecoRepository.findById(element.getId()).get();

				if(!enderecoTemp.getCep().equals(element.getCep())) {

					CepDto cepDto = pessoaUserService.consultaCep(element.getCep());

					element.setBairro(cepDto.getBairro());
					element.setCidade(cepDto.getLocalidade());
					element.setComplemento(cepDto.getComplemento());
					element.setRuaLogra(cepDto.getLogradouro());
					element.setUf(cepDto.getUf());

				}

			}
		}

		pessoaJuridica = pessoaUserService.salvarPessoajuridica(pessoaJuridica);

		return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);

	}


	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{

		if(pessoaFisica ==  null) {
			throw new ExceptionMentoriaJava("Pessoa fisica não pode sere NULL");
		}

		if(pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
		}

		if(pessoaFisica.getId() ==  null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}

		if(!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("Cnpj: " + pessoaFisica.getCpf() + " está inválido.");
		}

		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);

		return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);

	}

}
