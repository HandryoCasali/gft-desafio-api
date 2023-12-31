package br.com.gft.noticias.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gft.noticias.config.exception.EntityConflictException;
import br.com.gft.noticias.config.exception.EntityNotFoundException;
import br.com.gft.noticias.dtos.usuario.UsuarioAtualizacao;
import br.com.gft.noticias.dtos.usuario.UsuarioForm;
import br.com.gft.noticias.dtos.usuario.UsuarioMapper;
import br.com.gft.noticias.email.EmailModel;
import br.com.gft.noticias.email.EmailService;
import br.com.gft.noticias.entities.HistoricoEtiquetaUsuario;
import br.com.gft.noticias.entities.Perfil;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.HistoricoEtiquetaRepository;
import br.com.gft.noticias.repositories.PerfilRepository;
import br.com.gft.noticias.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HistoricoEtiquetaRepository historicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado no sistema"));

        return usuario;
    }

    public Usuario buscarUsuarioPorId(Long idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		if(optional.isEmpty()) {
			throw new EntityNotFoundException("Usuário não encontrado");
		}
		
		return optional.get();
	}

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    public Usuario cadastrar(UsuarioForm form){
        if(usuarioRepository.existsByEmail(form.email())){
            throw new EntityConflictException("Email já cadastrado no sistema");
        }
        Perfil perfil = perfilRepository.findByNome(form.perfil())
                                .orElseThrow(() -> new EntityNotFoundException("Perfil de acesso inexistente"));

        Usuario usuario = UsuarioMapper.toUsuario(form, perfil);

        usuarioRepository.save(usuario);
        
        EmailModel email = new EmailModel("handryogft@gmail.com", usuario.getEmail(), usuario.getNome(), "Sua conta no app de notícias foi criada com sucesso!");
        
        emailService.enviarEmail(email);
        return usuario;
    }
    
    @Transactional
    public void atualizarSenha(Usuario usuario, UsuarioAtualizacao form){
        usuario.setSenha(new BCryptPasswordEncoder().encode(form.novaSenha()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        
        List<HistoricoEtiquetaUsuario> historicos =  historicoRepository.findAllById_Usuario_Id(id);
        historicoRepository.deleteAll(historicos);

        usuarioRepository.deleteById(id);
    }
}
