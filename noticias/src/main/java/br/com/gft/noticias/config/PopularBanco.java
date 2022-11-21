package br.com.gft.noticias.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.gft.noticias.dtos.usuario.UsuarioForm;
import br.com.gft.noticias.entities.Perfil;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.PerfilRepository;
import br.com.gft.noticias.services.ConsumerApiNoticiaService;
import br.com.gft.noticias.services.EtiquetaService;
import br.com.gft.noticias.services.UsuarioService;

@Component
@Order(1)
public class PopularBanco implements ApplicationRunner{

    @Autowired PerfilRepository perfilRepository;
    @Autowired UsuarioService usuarioService;
    @Autowired EtiquetaService etiquetaService;
    @Autowired ConsumerApiNoticiaService consumerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        perfilRepository.save(new Perfil(null, "ROLE_ADMIN"));
        perfilRepository.save(new Perfil(null, "ROLE_USER"));

        usuarioService.cadastrar(new UsuarioForm("Admin", "adms@gft.com", "admin123", "ROLE_ADMIN"));

        Usuario usuario = usuarioService.cadastrar(new UsuarioForm("User", "user@gft.com", "user123", "ROLE_USER"));

        etiquetaService.cadastrarEtiquetaNoUsuarioLogado(usuario, "games");
        etiquetaService.cadastrarEtiquetaNoUsuarioLogado(usuario, "esportes");
        etiquetaService.cadastrarEtiquetaNoUsuarioLogado(usuario, "politica");
        etiquetaService.cadastrarEtiquetaNoUsuarioLogado(usuario, "copa 2022");
        
        consumerService.buscarNoticias(usuario);
    }
}
