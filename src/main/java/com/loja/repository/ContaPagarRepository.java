package com.loja.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loja.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

	@Query("select a from ContaPagar a where upper(trim(a.descricao)) like %?1%")
	List<ContaPagar> buscaContaDesc(String desc);

	@Query("select a from ContaPagar a where a.pessoa.id = ?1")
	List<ContaPagar> buscaContaPorPessoa(Long idPessoa);

	@Query("select a from ContaPagar a where a.pessoa_fornecedor.id = ?1")
	List<ContaPagar> buscaContaPorFornecedor(Long idFornecedor);

	@Query("select a from ContaPagar a where a.empresa.id = ?1")
	List<ContaPagar> buscaContaPorEmpresa(Long idEmpresa);

}
