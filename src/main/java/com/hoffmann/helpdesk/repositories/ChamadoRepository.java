package com.hoffmann.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoffmann.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer>{

}
