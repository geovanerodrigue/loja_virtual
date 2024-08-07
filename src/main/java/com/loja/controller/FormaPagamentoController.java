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
import com.loja.model.FormaPagamento;
import com.loja.repository.FormaPagamentoRepository;

@RestController
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarFormaPagamento")
	public ResponseEntity<FormaPagamento> salvarFormaPagamento(@RequestBody @Valid FormaPagamento formaPagamento) throws ExceptionMentoriaJava {


		formaPagamento  = formaPagamentoRepository.save(formaPagamento);

		return new ResponseEntity<>(formaPagamento, HttpStatus.OK);
	}


	@ResponseBody
	@GetMapping(value = "**/listaFormaPagamento/{idEmpresa}")
	public ResponseEntity<List<FormaPagamento>> listaFormaPagamentoidEmpresa(@PathVariable(value = "idEmpresa")Long idEmpresa){

		return new ResponseEntity<>(formaPagamentoRepository.findAll(idEmpresa), HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/listaFormaPagamento")
	public ResponseEntity<List<FormaPagamento>> listaFormaPagamentp(){

		return new ResponseEntity<>(formaPagamentoRepository.findAll(), HttpStatus.OK);

	}

}
