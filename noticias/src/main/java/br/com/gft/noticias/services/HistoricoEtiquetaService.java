package br.com.gft.noticias.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gft.noticias.entities.HistoricoEtiquetaUsuario;
import br.com.gft.noticias.repositories.HistoricoEtiquetaRepository;

@Service
public class HistoricoEtiquetaService {
    private final HistoricoEtiquetaRepository historicoRepository;

    public HistoricoEtiquetaService(HistoricoEtiquetaRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public Page<HistoricoEtiquetaUsuario> historicoEtiquetaDoUsuario(Long id, Pageable pageable){
        return historicoRepository.findAllById_Usuario_Id(id, pageable);
    }
}
