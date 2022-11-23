package br.com.gft.noticias.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.noticias.entities.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);

    List<Usuario> findAllByPerfil_Nome(String perfil);
}
