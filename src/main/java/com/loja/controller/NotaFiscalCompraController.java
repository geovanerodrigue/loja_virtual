package com.loja.controller;

import java.util.ArrayList;
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
import com.loja.model.NotaFiscalCompra;
import com.loja.model.NotaFiscalVenda;
import com.loja.model.dto.ObjetoRelatorioStatusCompra;
import com.loja.model.dto.ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO;
import com.loja.model.dto.ObjetoRequisicaoRelatorioProdutoAlertaEstoque;
import com.loja.repository.NotaFiscalCompraRepository;
import com.loja.repository.NotaFiscalVendaRepository;
import com.loja.service.NotaFiscalCompraService;

@RestController
public class NotaFiscalCompraController {

	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;

	@Autowired
    private NotaFiscalVendaRepository notaFiscalVendaRepository;
	
	@Autowired
	private NotaFiscalCompraService notaFiscalCompraService;
	
	
	@ResponseBody
	@PostMapping(value = "**/relatorioStatusCompra")
	public ResponseEntity<List<ObjetoRelatorioStatusCompra>> relatorioStatusCompra
	                      (@Valid @RequestBody ObjetoRelatorioStatusCompra objetoRelatorioStatusCompra) {
		
		List<ObjetoRelatorioStatusCompra> retorno = new ArrayList<ObjetoRelatorioStatusCompra>();
		
		retorno = notaFiscalCompraService.relatorioStatusVendaLojaVirtual(objetoRelatorioStatusCompra);
		
		return new  ResponseEntity<List<ObjetoRelatorioStatusCompra>>(retorno, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@PostMapping(value = "relatorioProdCompraNotaFiscal")
	public ResponseEntity<List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>> relatorioProdCompraNotaFiscal
	         (@Valid @RequestBody ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO objetoRequisicaoRelatorioProdCompraNotaFiscalDTO){

		List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO> retorno =
				 new ArrayList<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>();
		
		retorno = notaFiscalCompraService.gerarRelatorioProdCompraNota(objetoRequisicaoRelatorioProdCompraNotaFiscalDTO);
		
		return new ResponseEntity<List<ObjetoRequisicaoRelatorioProdCompraNotaFiscalDTO>>(retorno, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "relatorioProdAlertaEstoque")
	public ResponseEntity<List<ObjetoRequisicaoRelatorioProdutoAlertaEstoque>> relatorioProdAlertaEstoque
	         (@Valid @RequestBody ObjetoRequisicaoRelatorioProdutoAlertaEstoque objetoRequisicaoRelatorioProdutoAlertaEstoque){

		List<ObjetoRequisicaoRelatorioProdutoAlertaEstoque> retorno =
				 new ArrayList<ObjetoRequisicaoRelatorioProdutoAlertaEstoque>();
		
		retorno = notaFiscalCompraService.gerarRelatorioAlertaEstoque(objetoRequisicaoRelatorioProdutoAlertaEstoque);
		
		return new ResponseEntity<List<ObjetoRequisicaoRelatorioProdutoAlertaEstoque>>(retorno, HttpStatus.OK);
	}
	

	@ResponseBody
	@PostMapping(value = "**/salvarNotaFiscalCompra")
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionMentoriaJava {

		if(notaFiscalCompra.getId() == null) {

			if(notaFiscalCompra.getDescricaoObs() != null) {
				boolean existe = notaFiscalCompraRepository.existeNotaComDescricao(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());

				if(existe){
					throw new ExceptionMentoriaJava("Já esxiste  nota de compra com essa mesma descrição: " + notaFiscalCompra.getDescricaoObs());
				}
			}

		}

		if(notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A pessoa jurídica da nota fiscal deve ser informada.");
		}

		if(notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <= 0) {
			throw new ExceptionMentoriaJava("A empresa responsável deve ser informada.");
		}

		if(notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <= 0) {
			throw new ExceptionMentoriaJava("A conta a pagar da nota deve ser informada.");
		}

		NotaFiscalCompra notaFiscalCompraSalvo = notaFiscalCompraRepository.save(notaFiscalCompra);

		return new ResponseEntity<>(notaFiscalCompraSalvo, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/buscarNotaFiscalPordDesc/{desc}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarNotaFiscalPorDesc(@PathVariable("desc") String desc) {

		List<NotaFiscalCompra> notaFiscalCompra = notaFiscalCompraRepository.buscaNotaDesc(desc.toUpperCase().trim());

		return new ResponseEntity<>(notaFiscalCompra, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/obterNotaFiscalCompra/{id}")
	public ResponseEntity<NotaFiscalCompra> obterNotaFiscalCompra(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

		if(notaFiscalCompra == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado a nota fiscal com o código: " + id);
		}

		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterNotaFiscalCompraDaVenda/{idvenda}")
	public ResponseEntity<List<NotaFiscalVenda>> obterNotaFiscalCompraDaVenda(@PathVariable("idvenda") Long idvenda) throws ExceptionMentoriaJava {

		List<NotaFiscalVenda> notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVenda(idvenda);

		if(notaFiscalCompra == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado a nota fiscal de venda com o código: " + idvenda);
		}

		return new ResponseEntity<List<NotaFiscalVenda>>(notaFiscalCompra, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterNotaFiscalCompraDaVendaUnico/{idvenda}")
	public ResponseEntity<NotaFiscalVenda> obterNotaFiscalCompraDaVendaUnico(@PathVariable("idvenda") Long idvenda) throws ExceptionMentoriaJava {

		NotaFiscalVenda notaFiscalCompra = notaFiscalVendaRepository.buscaNotaPorVendaUnica(idvenda);

		if(notaFiscalCompra == null) {
			throw new ExceptionMentoriaJava("Não foi encotrado a nota fiscal de venda com o código: " + idvenda);
		}

		return new ResponseEntity<NotaFiscalVenda>(notaFiscalCompra, HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteNotaFiscalCompraPorId/{id}")
	public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {

		//delete os filhos
		notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);

		//delete o pai
		notaFiscalCompraRepository.deleteById(id);

		return new ResponseEntity("Nota fiscal Removida",HttpStatus.OK);
	}

}
