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
import com.loja.model.MarcaProduto;
import com.loja.repository.MarcaRepository;

@Controller
@RestController
public class MarcaController {


	@Autowired
	private MarcaRepository marcaRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarMarca")
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {

		if(marcaProduto.getId() == null) {
		List<MarcaProduto> marcaProdutos = marcaRepository.buscarMarcaDes(marcaProduto.getNomeDesc().toUpperCase());
		if(!marcaProdutos.isEmpty()) {
			throw new ExceptionMentoriaJava("Já existe acesso com essa descrição: " + marcaProduto.getNomeDesc());
		  }
		}
		MarcaProduto marcaProdutoSalvo = marcaRepository.save(marcaProduto);

		return new ResponseEntity<>(marcaProdutoSalvo, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/deleteMarca")
	public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) {

		marcaRepository.deleteById(marcaProduto.getId());

		return new ResponseEntity("Marca Removida",HttpStatus.OK);
	}

	//@Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@DeleteMapping(value = "**/deleteMarcaPorId/{id}")
	public ResponseEntity<?> deleteMarcaPorId(@PathVariable("id") Long id) {

		marcaRepository.deleteById(id);

		return new ResponseEntity("Marca Removido",HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/obterMarca/{id}")
	public ResponseEntity<MarcaProduto> obterMarca(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		MarcaProduto marcaProduto = marcaRepository.findById(id).orElse(null);

		if(marcaProduto == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado a Marca com o código: " + id);
		}

		return new ResponseEntity<>(marcaProduto, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarMarcaPordDesc/{desc}")
	public ResponseEntity<List<MarcaProduto>> buscarMarcaPorDesc(@PathVariable("desc") String desc) {

		List<MarcaProduto> marcaProduto = marcaRepository.buscarMarcaDes(desc.toUpperCase());

		return new ResponseEntity<>(marcaProduto, HttpStatus.OK);
	}

}
