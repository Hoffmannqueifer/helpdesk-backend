package com.hoffmann.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hoffmann.helpdesk.domain.Chamado;
import com.hoffmann.helpdesk.domain.Cliente;
import com.hoffmann.helpdesk.domain.Tecnico;
import com.hoffmann.helpdesk.domain.enums.Perfil;
import com.hoffmann.helpdesk.domain.enums.Prioridade;
import com.hoffmann.helpdesk.domain.enums.Status;
import com.hoffmann.helpdesk.repositories.ChamadoRepository;
import com.hoffmann.helpdesk.repositories.ClienteRepository;
import com.hoffmann.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec = new Tecnico(null, "hoffmann", "75554426042", "hoffmann@gmail.com", "123" );
		tec.addPerfil(Perfil.ADMIN);
		
		Cliente cli = new Cliente(null, "teste", "98814796068", "teste@gmail.com", "234");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec, cli);
		
		tecnicoRepository.saveAll(Arrays.asList(tec));
		clienteRepository.saveAll(Arrays.asList(cli));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
