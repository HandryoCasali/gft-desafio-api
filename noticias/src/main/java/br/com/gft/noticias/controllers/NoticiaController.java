package br.com.gft.noticias.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.noticias.dtos.noticia.ListagemNoticiaDTO;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.services.ConsumerApiNoticiaService;

@RestController
@RequestMapping("v1/noticias")
public class NoticiaController {
    private final ConsumerApiNoticiaService consumerService;

    public NoticiaController(ConsumerApiNoticiaService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping
    @RolesAllowed("USER")
    public ListagemNoticiaDTO buscarNoticias(@AuthenticationPrincipal Usuario usuario){
        return consumerService.buscarNoticias(usuario);
    }
}
