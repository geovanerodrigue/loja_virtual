package com.loja.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.loja.model.StatusRastreio;
import com.loja.repository.StatusRastreioRepository;

@RestController
public class StatusRastreioController {

	@Autowired
    private StatusRastreioRepository statusRastreioRepository;

	@ResponseBody
	@GetMapping(value = "**/listaRastreioVenda/{idVenda}")
	public ResponseEntity<List<StatusRastreio>> listaRastreioVenda(@PathVariable("idVenda") Long idVenda){

		List<StatusRastreio> statusRastreios = statusRastreioRepository.listaRastreioVenda(idVenda);

		return new ResponseEntity<>(statusRastreios, HttpStatus.OK);
	}

}
