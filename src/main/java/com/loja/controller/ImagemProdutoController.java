package com.loja.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.loja.model.ImagemProduto;
import com.loja.model.dto.ImagemProdutoDTO;
import com.loja.repository.ImagemProdutoRepository;

@RestController
public class ImagemProdutoController {

	@ResponseBody
	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto){

		imagemProduto = imagemProdutoRepository.saveAndFlush(imagemProduto);

		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagemProduto.getId());
		imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
		imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
		imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
		imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());

		return new ResponseEntity<>(imagemProdutoDTO, HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteTodoImagemProduto/{idproduto}")
	public ResponseEntity<?> deleteTodoImagemProduto(@PathVariable("idProduto") Long idProduto){

		imagemProdutoRepository.deleteImagens(idProduto);

		return new ResponseEntity<>("Imagens removidas!", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteImagemObjeto")
	public ResponseEntity<?> deleteImagemObjeto(@RequestBody ImagemProduto imagemProduto){

		if(!imagemProdutoRepository.existsById(imagemProduto.getId())) {
			return new ResponseEntity<>("Imagem já foi removida ou não existe com esse id: " + imagemProduto.getId(), HttpStatus.OK);
		}

		imagemProdutoRepository.deleteById(imagemProduto.getId());

		return new ResponseEntity<>("Imagem removida!", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deleteImagemProdutoPorId/{id}")
	public ResponseEntity<?> deleteImagemProdutoPorId(@PathVariable("id") Long id){

		if(!imagemProdutoRepository.existsById(id)) {
			return new ResponseEntity<>("Imagem já foi removida ou não existe com esse id: " + id, HttpStatus.OK);
		}

		imagemProdutoRepository.deleteById(id);

		return new ResponseEntity<>("Imagem removida!", HttpStatus.OK);

	}

	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;

	@ResponseBody
	@GetMapping(value = "**/obterImagemPorProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obtertImagemProduto(@PathVariable("idProduto") Long idProduto){

		List<ImagemProdutoDTO> dtos = new ArrayList<>();

		List<ImagemProduto> imagemProdutos = imagemProdutoRepository.buscaImagemProduto(idProduto);

		for(ImagemProduto imagemProduto : imagemProdutos) {

			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
			imagemProdutoDTO.setId(imagemProduto.getId());
			imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
			imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
			imagemProdutoDTO.setImagemMiniatura(imagemProduto.getImagemMiniatura());
			imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());

			dtos.add(imagemProdutoDTO);

		}

		return new ResponseEntity<>(dtos, HttpStatus.OK);

	}

}
