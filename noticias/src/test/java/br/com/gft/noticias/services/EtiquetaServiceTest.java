package br.com.gft.noticias.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
import br.com.gft.noticias.config.exception.EntityNotFoundException;
import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.EtiquetaRepository;
import br.com.gft.noticias.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EtiquetaService.class)
public class EtiquetaServiceTest {
    @Autowired
    private EtiquetaService service;

    @MockBean
    private EtiquetaRepository etiquetaRepository;
    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveCadastrarEtiquetaNoUsuario_RetornarExcecaoSeUsuarioJaTemEtiqueta() {
        String nomeEtiqueta = "etiqueta";
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        when(etiquetaRepository.findByNome(nomeEtiqueta))
                .thenReturn(Optional.of(new Etiqueta(1l, "etiqueta", 0L, null)));
        ;
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.cadastrarEtiquetaNoUsuario(usuario, nomeEtiqueta));
    }

    @Test
    public void deveCadastrarEtiquetaNoUsuario_SeEtiquetaJaEstiverCadastradaApenasAdicionaOUsuario() {
        String nomeEtiqueta = "etiqueta";
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        Etiqueta etiqueta = new Etiqueta(1l, "etiqueta", 0L, new ArrayList<>());
        when(etiquetaRepository.findByNome(nomeEtiqueta)).thenReturn(Optional.of(etiqueta));
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(false);

        service.cadastrarEtiquetaNoUsuario(usuario, nomeEtiqueta);
        assertTrue(etiqueta.getUsuarios().contains(usuario));
    }

    @Test
    public void deveCadastrarEtiquetaNoUsuario_SeEtiquetaNaoExistirCadastraEAdicionaNoUsuario() {
        String nomeEtiqueta = "etiqueta";
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);

        when(etiquetaRepository.findByNome(nomeEtiqueta)).thenReturn(Optional.empty());
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(false);

        Etiqueta etiquetaSalva = service.cadastrarEtiquetaNoUsuario(usuario, nomeEtiqueta);
        assertAll(
                () -> assertTrue(etiquetaSalva.getUsuarios().contains(usuario)),
                () -> verify(this.etiquetaRepository).save(any()));
    }

    @Test
    public void deveExcluirEtiquetaDoUsuario_RetornarExcecaoSeNaoExistirAEtiquetaNoUsuario() {
        String nomeEtiqueta = "etiqueta";
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deletarEtiquetaNoUsuario(usuario, nomeEtiqueta));
    }

    @Test
    public void deveExcluirEtiquetaDoUsuario() {
        String nomeEtiqueta = "etiqueta";
        Etiqueta etiqueta = new Etiqueta(1l, "etiqueta", 0L, new ArrayList<>());
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        usuario.getEtiquetas().add(etiqueta);
        when(etiquetaRepository.existsByNomeAndUsuarios_Email(nomeEtiqueta, usuario.getEmail())).thenReturn(true);
        when(etiquetaRepository.findByNome(nomeEtiqueta)).thenReturn(Optional.of(etiqueta));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        service.deletarEtiquetaNoUsuario(usuario, nomeEtiqueta);
        assertFalse(usuario.getEtiquetas().contains(etiqueta));
    }

    @Test
    public void deveAtualizarAQuatidadeDeAcessoDaEtiqueta() {
        Etiqueta etiqueta = new Etiqueta(1l, "etiqueta", 0L, new ArrayList<>());
        service.atualizarAcessoEtiqueta(etiqueta);
        assertEquals(1, etiqueta.getQntPesquisa());
    }

}
