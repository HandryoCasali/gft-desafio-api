package br.com.gft.noticias.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.noticias.entities.HistoricoEtiquetaUsuario;
import br.com.gft.noticias.entities.pk.HistoricoEtiquetaUsuarioPK;

public interface HistoricoEtiquetaRepository extends JpaRepository <HistoricoEtiquetaUsuario, HistoricoEtiquetaUsuarioPK> {

    Page<HistoricoEtiquetaUsuario> findAllById_Usuario_Id(Long id, Pageable pageable);

    List<HistoricoEtiquetaUsuario> findAllById_Usuario_Id(Long id);
    
}
