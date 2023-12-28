package com.hoffmann.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoffmann.helpdesk.domain.Cliente;
import com.hoffmann.helpdesk.domain.Pessoa;
import com.hoffmann.helpdesk.domain.dtos.ClienteDTO;
import com.hoffmann.helpdesk.repositories.ClienteRepository;
import com.hoffmann.helpdesk.repositories.PessoaRepository;
import com.hoffmann.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.hoffmann.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado: ID: "+ id));
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return clienteRepository.save(newObj);
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
		}
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente updObj = findById(id);
		validaPorCpfEEmail(objDTO);
		updObj = new Cliente(objDTO);
		return clienteRepository.save(updObj);
	}

	public void delete(Integer id) {
		Cliente delObj = findById(id);
		if(delObj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado");
		}
		clienteRepository.deleteById(id);
	}
}
