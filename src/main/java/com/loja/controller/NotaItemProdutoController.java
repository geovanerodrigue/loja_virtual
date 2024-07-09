package com.loja.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.ExceptionMentoriaJava;
import com.loja.model.NotaItemProduto;
import com.loja.repository.NotaItemProdutoRepository;

@RestController
public class NotaItemProdutoController {

	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarNotaItemProduto")
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto) throws ExceptionMentoriaJava {

		if(notaItemProduto.getId() == null) {

			if(notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
				throw new  ExceptionMentoriaJava("O produto deve ser informado!");
			}

			if(notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
				throw new  ExceptionMentoriaJava("A nota fiscal deve ser informada!");
			}

			if(notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
				throw new  ExceptionMentoriaJava("A empresa deve ser informada!");
			}

			List<NotaItemProduto> notaExistente = notaItemProdutoRepository
					.buscaNotaItemProdutoNota(notaItemProduto.getProduto().getId(),notaItemProduto.getNotaFiscalCompra().getId());

			if(!notaExistente.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe esse produto cadastrado para esta nota!");
			}
		}


		if(notaItemProduto.getQuantidade() <= 0) {
			throw new ExceptionMentoriaJava("A quantidade deve ser informada!");
		}

		NotaItemProduto notaItemProdutoSalva = notaItemProdutoRepository.save(notaItemProduto);

		notaItemProdutoSalva = notaItemProdutoRepository.findById(notaItemProduto.getId()).get();

		return new ResponseEntity<>(notaItemProdutoSalva, HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteNotaItemProdutoPorId/{id}")
	public ResponseEntity<?> deleteNotaItemProdutoPorId(@PathVariable("id") Long id) {

		notaItemProdutoRepository.deleteByIdNotaItem(id);

		return new ResponseEntity("Nota Item Produto Removido!",HttpStatus.OK);
	}

}
