package br.com.gft.noticias.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.gft.noticias.config.exception.EntityConflictException;
import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.EtiquetaRepository;
import br.com.gft.noticias.repositories.UsuarioRepository;

@Service
public class EtiquetaService {
    private final EtiquetaRepository etiquetaRepository;
    private final UsuarioRepository usuarioRepository;
    public EtiquetaService(EtiquetaRepository etiquetaRepository, UsuarioRepository usuarioRepository) {
        this.etiquetaRepository = etiquetaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Etiqueta> listarEtiquetas(Pageable pageable){
        return etiquetaRepository.findAll(pageable);
    }

    @Transactional
    public Etiqueta cadastrarEtiquetaNoUsuarioLogado(Usuario usuario, String nomeEtiqueta){
        Optional<Etiqueta> opt = etiquetaRepository.findByNome(nomeEtiqueta);
        Etiqueta etiqueta = null;
        if(opt.isPresent()){
            if(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())){
                throw new EntityConflictException("Etiqueta já esta cadastrada nesse usuário");
            }
            etiqueta = opt.get();
            etiqueta.getUsuarios().add(usuario);
        } else {
            etiqueta = new Etiqueta();
            etiqueta.setNome(nomeEtiqueta);
            etiqueta.getUsuarios().add(usuario);
            etiquetaRepository.save(etiqueta);
        }

        return etiqueta;
    }

    @Transactional
    public void deletarEtiquetaNoUsuarioLogado(Usuario usuario, String nomeEtiqueta){
        if(!etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())){
            throw new EntityConflictException("Etiqueta já esta cadastrada nesse usuário");
        }
        Etiqueta etiqueta = etiquetaRepository.findByNome(nomeEtiqueta).get();
        usuario = usuarioRepository.findById(usuario.getId()).get();
        usuario.getEtiquetas().remove(etiqueta);
    }
}
