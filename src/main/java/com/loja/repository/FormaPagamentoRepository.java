package com.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.loja.model.FormaPagamento;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
