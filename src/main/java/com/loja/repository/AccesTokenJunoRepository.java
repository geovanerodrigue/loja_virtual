package com.loja.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loja.model.AccesTokenJunoAPI;

@Repository
@Transactional
public interface AccesTokenJunoRepository extends JpaRepository<AccesTokenJunoAPI, Long> {

}
