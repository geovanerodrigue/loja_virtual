package com.loja.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.ExceptionMentoriaJava;
import com.loja.model.CupDesconto;
import com.loja.repository.CupDescontoRepository;

@RestController
public class CupDescontoController {
	
	@Autowired
	private CupDescontoRepository cupDescontoRepository;
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteCupomDesc/{id}")
	public ResponseEntity<CupDesconto> deletarCupomDesc(@PathVariable("id") Long id){
		
		cupDescontoRepository.deleteById(id);
		
		return new ResponseEntity("Cupom Removido", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterCupom/{id}")
	public ResponseEntity<CupDesconto> obterCupom(@PathVariable("id") Long id) throws ExceptionMentoriaJava{
		
		CupDesconto cupDesc = cupDescontoRepository.findById(id).orElse(null);
		
		if(cupDesc == null) {
			throw new ExceptionMentoriaJava("Não foi encontrado Cupom com o código: " + id);
		}
		
		return new ResponseEntity<CupDesconto>(cupDesc, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarCupomDesc")
	public ResponseEntity<CupDesconto> salvarCupomDesc(@RequestBody @Valid CupDesconto cupDesconto){
		
		CupDesconto cupDesconto2 = cupDescontoRepository.save(cupDesconto);
		
		return new ResponseEntity<CupDesconto>(cupDesconto2, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/listasCupomDesc/{idEmpresa}")
	public ResponseEntity<List<CupDesconto>> listasCupomDesc(@PathVariable("idEmpresa")Long idEmpresa){
		
		return new ResponseEntity<List<CupDesconto>>(cupDescontoRepository.cupomDescontoPorEmpresa(idEmpresa), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/listasCupomDesc")
	public ResponseEntity<List<CupDesconto>> listasCupomDesc(){
		
		return new ResponseEntity<List<CupDesconto>>(cupDescontoRepository.findAll(), HttpStatus.OK);
	}

}
