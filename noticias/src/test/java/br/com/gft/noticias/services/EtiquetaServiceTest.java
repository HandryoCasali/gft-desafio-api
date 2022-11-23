package br.com.gft.noticias.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.noticias.config.exception.EntityConflictException;
import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.EtiquetaRepository;
import br.com.gft.noticias.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EtiquetaService.class)
public class EtiquetaServiceTest {
    @Autowired private EtiquetaService service;
    
    @MockBean private EtiquetaRepository etiquetaRepository;
    @MockBean private UsuarioRepository usuarioRepository;

    @Test
    public void deveCadastrarEtiquetaNoUsuario_RetornarExcecaoSeUsuarioJaTemEtiqueta(){
        String nomeEtiqueta = "etiqueta";
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        when(etiquetaRepository.findByNome(nomeEtiqueta)).thenReturn(Optional.of(new Etiqueta(1l, "etiqueta", 0L, null)));;
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(true);

        assertThrows(EntityConflictException.class , () -> service.cadastrarEtiquetaNoUsuario(usuario, nomeEtiqueta));
    }
}
