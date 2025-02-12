package br.com.gft.noticias.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.noticias.config.exception.EntityConflictException;
import br.com.gft.noticias.config.exception.EntityNotFoundException;
import br.com.gft.noticias.dtos.usuario.UsuarioForm;
import br.com.gft.noticias.email.EmailService;
import br.com.gft.noticias.entities.Perfil;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.HistoricoEtiquetaRepository;
import br.com.gft.noticias.repositories.PerfilRepository;
import br.com.gft.noticias.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsuarioService.class)
public class UsuarioServiceTest {
    @Autowired private UsuarioService usuarioService;

    @MockBean private UsuarioRepository usuarioRepository;
    @MockBean private PerfilRepository perfilRepository;
    @MockBean private EmailService emailService;
    @MockBean private HistoricoEtiquetaRepository historicoRepository;

    public static Usuario usuario;

    @BeforeEach
    public void setup(){
        usuario = 
            new Usuario(1L, "nome", "email@email.com", "1234", null, null);
    }

    @Test
    public void deveBuscarUsuarioPorId_RetornarExcecaoSeNaoEncontrarUsuario(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->  usuarioService.buscarUsuarioPorId(2L));
    }

    @Test
    public void deveBuscarUsuarioPorId_RetornarUsuarioExistente(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario usuario1 = usuarioService.buscarUsuarioPorId(1L);
        assertEquals(usuario, usuario1);
    }

    @Test
    public void deveBuscarUsuarioPorEmail_RetornarExcecaoSeNaoEncontrarUsuario(){
        when(usuarioRepository.findByEmail("fake@email.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () ->  usuarioService.loadUserByUsername("fake@email.com"));
    }

    @Test
    public void deveBuscarUsuarioPorEmail_RetornarUserDetailsSeEncontrarUsuario(){
        when(usuarioRepository.findByEmail("email@email.com")).thenReturn(Optional.of(usuario));
        UserDetails usuarioBuscado = usuarioService.loadUserByUsername("email@email.com");
        assertEquals((UserDetails) usuario, usuarioBuscado);
    }

    @Test
    public void deveCadastrarUsuario_RetornarExcecaoSeJaExisteEmailCadastrado(){
        when(usuarioRepository.existsByEmail("email@email.com")).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> usuarioService.cadastrar(new UsuarioForm("nome", "email@email.com", "12345678", "perfil")));
    }

    @Test
    public void deveCadastrarUsuario(){
        when(usuarioRepository.existsByEmail("email@email.com")).thenReturn(false);
        when(perfilRepository.findByNome("perfil")).thenReturn(Optional.of(new Perfil(1L, "ROLE_USER")));
        when(usuarioRepository.save(null)).thenReturn(Optional.of(usuario));

        Usuario usuarioCadastrado = usuarioService.cadastrar(new UsuarioForm("nome", "email@email.com", "1234", "perfil"));
        
        assertAll(
           () -> assertEquals("email@email.com", usuarioCadastrado.getEmail()),
           () -> verify(this.emailService, times(1)).enviarEmail(any())
        );
    }

    @Test
    public void deveDeletarUsuario_RetornarExcecaoSeNaoExistirIdCadastrado(){
        when(usuarioRepository.existsById(2L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> usuarioService.excluir(2L));
    }

    @Test
    public void deveDeletarUsuario(){
        when(usuarioRepository.existsById(2L)).thenReturn(true);
        usuarioService.excluir(2L);
        assertAll(
            () -> verify(usuarioRepository).deleteById(2L),
            () -> verify(historicoRepository).deleteAll(any())
        );
    }

}
