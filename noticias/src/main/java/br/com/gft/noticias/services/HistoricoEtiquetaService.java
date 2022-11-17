package br.com.gft.noticias.services;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.HistoricoEtiquetaUsuario;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.entities.pk.HistoricoEtiquetaUsuarioPK;
import br.com.gft.noticias.repositories.EtiquetaRepository;
import br.com.gft.noticias.repositories.HistoricoEtiquetaRepository;

@Service
public class HistoricoEtiquetaService {
    private final HistoricoEtiquetaRepository historicoRepository;
    private final EtiquetaRepository etiquetaRepository;

    public HistoricoEtiquetaService(HistoricoEtiquetaRepository historicoRepository, EtiquetaRepository etiquetaRepository) {
        this.historicoRepository = historicoRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    public Page<HistoricoEtiquetaUsuario> historicoEtiquetaDoUsuario(Long id, Pageable pageable){
        return historicoRepository.findAllById_Usuario_Id(id, pageable);
    }

    @Transactional
    public void cadastrarHistoricoEtiquetaUsuario(Usuario usuario, Etiqueta etiqueta){
        var novoHistorico = new HistoricoEtiquetaUsuario(new HistoricoEtiquetaUsuarioPK(usuario, etiqueta));
        etiqueta.setQntPesquisa(etiqueta.getQntPesquisa() + 1);
        historicoRepository.save(novoHistorico);
        etiquetaRepository.save(etiqueta);
    }
}
