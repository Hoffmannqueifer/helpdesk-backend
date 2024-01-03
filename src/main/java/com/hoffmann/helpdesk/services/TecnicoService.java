package com.hoffmann.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoffmann.helpdesk.domain.Pessoa;
import com.hoffmann.helpdesk.domain.Tecnico;
import com.hoffmann.helpdesk.domain.dtos.TecnicoDTO;
import com.hoffmann.helpdesk.repositories.PessoaRepository;
import com.hoffmann.helpdesk.repositories.TecnicoRepository;
import com.hoffmann.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.hoffmann.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Tecnico não encontrado: ID: "+ id));
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return tecnicoRepository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
		}
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico updObj = findById(id);
		validaPorCpfEEmail(objDTO);
		updObj = new Tecnico(objDTO);
		return tecnicoRepository.save(updObj);
	}

	public void delete(Integer id) {
		Tecnico delObj = findById(id);
		if(delObj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado");
		}
		tecnicoRepository.deleteById(id);
	}
}
