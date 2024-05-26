package com.loja.controller;

import java.util.List;

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
import com.loja.model.Acesso;
import com.loja.repository.AcessoRepository;
import com.loja.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {
		
		if(acesso.getId() == null) {
		List<Acesso> acessos = acessoRepository.buscarAcessoDes(acesso.getDescricao().toUpperCase());
		if(!acessos.isEmpty()) {
			throw new ExceptionMentoriaJava("Já existe acesso com essa descrição: " + acesso.getDescricao());
		  }
		}
		Acesso acessoSalvo = acessoService.save(acesso);

		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/deleteAcesso")
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {

		 acessoRepository.deleteById(acesso.getId());

		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}

	//@Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) {

		 acessoRepository.deleteById(id);

		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		Acesso acesso = acessoRepository.findById(id).orElse(null);

		if(acesso == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado o Acesso com o código: " + id);
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarPordDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {

		List<Acesso> acesso = acessoRepository.buscarAcessoDes(desc.toUpperCase());

		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
	}

}
