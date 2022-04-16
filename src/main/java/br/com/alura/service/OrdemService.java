package br.com.alura.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import br.com.alura.model.Ordem;
import br.com.alura.model.Usuario;
import br.com.alura.repository.OrdemRepository;

@ApplicationScoped
public class OrdemService {
    
    @Inject
    OrdemRepository repository;

    public void inserir(@Context SecurityContext securityContext, Ordem ordem) {
        Optional<Usuario> usuarioOptional = Usuario.findByIdOptional(ordem.getUserId());
        Usuario usuario = usuarioOptional.orElseThrow();

        if(!usuario.getUsername().equals(securityContext.getUserPrincipal().getName())) {
            throw new RuntimeException("Usuário logado é diferente do User ID");
        }
        ordem.setData(Date.valueOf(LocalDate.now()));
        ordem.setStatus("ENVIADA");
        repository.persist(ordem);
    }

    public List<Ordem> buscarOrdens() {
        return repository.listAll();
    }
}
