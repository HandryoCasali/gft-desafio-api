package br.com.gft.noticias.dtos.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gft.noticias.dtos.etiqueta.EtiquetaMapper;
import br.com.gft.noticias.entities.Perfil;
import br.com.gft.noticias.entities.Usuario;

public class UsuarioMapper {


    public static UsuarioDTO toUsuarioDTO(Usuario usuario){
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public static DetalhesUsuarioDTO toDetalhesUsuario(Usuario usuario) {
        return new DetalhesUsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil().getNome().toString(), usuario.getEtiquetas().stream().map(EtiquetaMapper::toEtiquetaDTO).toList());
    }

    
    public static Usuario toUsuario(UsuarioForm form, Perfil perfil) {
        return new Usuario(form.nome(), form.email(), new BCryptPasswordEncoder().encode(form.senha()), perfil);
    };
    
}
