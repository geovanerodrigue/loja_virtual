package com.loja.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.ExceptionMentoriaJava;
import com.loja.model.ContaPagar;
import com.loja.repository.ContaPagarRepository;

@Controller
@RestController
public class ContaPagarController {



	@Autowired
    private ContaPagarRepository contaPagarRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarContaPagar")
	public ResponseEntity<ContaPagar> salvarProduto(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionMentoriaJava {


		if(contaPagar.getId() == null) {
			List<ContaPagar> contaPagars = contaPagarRepository.buscaContaDesc(contaPagar.getDescricao().toUpperCase().trim());

			if(!contaPagars.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe Conta a Pagar com a descrição: " + contaPagar.getDescricao());
			}

			if(contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
				throw new ExceptionMentoriaJava("Empresa responsável deve ser informada");
			}

			if(contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
				throw new ExceptionMentoriaJava("Pessoa responsável deve ser informada");
			}

			if(contaPagar.getPessoa_fornecedor() == null || contaPagar.getPessoa_fornecedor().getId() <= 0) {
				throw new ExceptionMentoriaJava("Fornecedor responsável deve ser informado");
			}
		}

		ContaPagar contaPagarSalvo = contaPagarRepository.save(contaPagar);

		return new ResponseEntity<>(contaPagarSalvo, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/deleteContaPagar")
	public ResponseEntity<String> deleteProduto(@RequestBody ContaPagar contaPagar) {

		contaPagarRepository.deleteById(contaPagar.getId());

		return new ResponseEntity<>("ProdutoRemovido",HttpStatus.OK);
	}


	//@Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@DeleteMapping(value = "**/deleteContaPagarPorId/{id}")
	public ResponseEntity<String> deleteProdutoPorId(@PathVariable("id") Long id) {

		contaPagarRepository.deleteById(id);

		return new ResponseEntity<>("Conta a pagar Removida",HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/obterContaPagarPorId/{id}")
	public ResponseEntity<ContaPagar> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);

		if(contaPagar == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado o Produto com o código: " + id);
		}

		return new ResponseEntity<>(contaPagar, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarContaPagarNome/{nome}")
	public ResponseEntity<List<ContaPagar>> buscarContaPagarDesc(@PathVariable("nome") String nome) {

		List<ContaPagar> contaPagar = contaPagarRepository.buscaContaDesc(nome.toUpperCase());

		return new ResponseEntity<>(contaPagar, HttpStatus.OK);
	}

}
