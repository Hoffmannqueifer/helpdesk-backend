package com.hoffmann.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoffmann.helpdesk.domain.Chamado;
import com.hoffmann.helpdesk.repositories.ChamadoRepository;
import com.hoffmann.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findByID(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado! ID:" + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
}
