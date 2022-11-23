package br.com.gft.noticias.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.HistoricoEtiquetaRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HistoricoEtiquetaService.class)
public class HistoricoEtiquetaServiceTest {
    @Autowired private HistoricoEtiquetaService service;

    @MockBean private HistoricoEtiquetaRepository repository;

    @Test
    public void deveCadastrarHistorico(){
        Etiqueta etiqueta = new Etiqueta(1l, "etiqueta", 0L, new ArrayList<>());
        Usuario usuario = new Usuario(1L, "nome", "email@email.com", "senha", new ArrayList<>(), null);
        
        service.cadastrarHistoricoEtiquetaUsuario(usuario, etiqueta); 
        verify(this.repository).save(any());
    }
}
