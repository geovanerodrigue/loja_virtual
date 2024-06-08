package com.loja.controller;

import java.util.List;

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
import com.loja.model.Acesso;
import com.loja.model.CategoriaProduto;
import com.loja.model.dto.CategoriaProdutoDto;
import com.loja.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@ResponseBody
	@GetMapping(value = "**/buscarPordDescCategoria/{desc}")
	public ResponseEntity<List<CategoriaProduto>> buscarPorDesc(@PathVariable("desc") String desc) {

		List<CategoriaProduto> categoria = categoriaProdutoRepository.buscarCategoriaDes(desc.toUpperCase());

		return new ResponseEntity<List<CategoriaProduto>>(categoria, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteCategoria")
	public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) {

		if(categoriaProdutoRepository.findById(categoriaProduto.getId()).isPresent() == false) {
			return new ResponseEntity("Categoria já foi Removida", HttpStatus.OK);
		}
		
		 categoriaProdutoRepository.deleteById(categoriaProduto.getId());

		return new ResponseEntity("Categoria Removida", HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDto> salvarCategoria (@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {
		
		if(categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null)) {
			throw new  ExceptionMentoriaJava("A empresa deve ser informada");
		}
		
		if(categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("A categoria já existe");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDto catgoriaProdutoDto = new CategoriaProdutoDto();
		catgoriaProdutoDto.setId(categoriaSalva.getId());
		catgoriaProdutoDto.setNomeDesc(categoriaSalva.getNomeDesc());
		catgoriaProdutoDto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		
		return new ResponseEntity<CategoriaProdutoDto>(catgoriaProdutoDto, HttpStatus.OK);
	}

}
