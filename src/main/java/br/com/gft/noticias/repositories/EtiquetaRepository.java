package br.com.gft.noticias.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.noticias.entities.Etiqueta;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long>{
    Optional<Etiqueta> findByNome(String nome);
   
    boolean existsByNomeAndUsuarios_Email(String nome, String email);

}
