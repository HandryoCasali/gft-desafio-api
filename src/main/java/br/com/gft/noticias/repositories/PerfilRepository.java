package br.com.gft.noticias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.noticias.entities.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{
    Optional<Perfil> findByNome(String nome);
}
