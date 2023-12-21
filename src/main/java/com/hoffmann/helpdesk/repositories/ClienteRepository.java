package com.hoffmann.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoffmann.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

}
