package com.hoffmann.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoffmann.helpdesk.domain.Chamado;
import com.hoffmann.helpdesk.domain.Cliente;
import com.hoffmann.helpdesk.domain.Tecnico;
import com.hoffmann.helpdesk.domain.enums.Perfil;
import com.hoffmann.helpdesk.domain.enums.Prioridade;
import com.hoffmann.helpdesk.domain.enums.Status;
import com.hoffmann.helpdesk.repositories.ChamadoRepository;
import com.hoffmann.helpdesk.repositories.ClienteRepository;
import com.hoffmann.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {
		Tecnico tec = new Tecnico(null, "hoffmann", "75554426042", "hoffmann@gmail.com", encoder.encode("123") );
		tec.addPerfil(Perfil.ADMIN);
		
		Cliente cli = new Cliente(null, "teste", "98814796068", "teste@gmail.com", encoder.encode("123"));
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec, cli);
		
		tecnicoRepository.saveAll(Arrays.asList(tec));
		clienteRepository.saveAll(Arrays.asList(cli));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
}
