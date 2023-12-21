package com.hoffmann.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoffmann.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa,Integer>{

}
