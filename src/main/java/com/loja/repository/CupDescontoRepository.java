package com.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.loja.model.CupDesconto;

@Repository
public interface CupDescontoRepository extends JpaRepository<CupDesconto, Long> {

	@Query(value = "select c from CupDesconto c where c.empresa.id = ?1")
	public List<CupDesconto> cupomDescontoPorEmpresa(Long idEmpresa);

}
