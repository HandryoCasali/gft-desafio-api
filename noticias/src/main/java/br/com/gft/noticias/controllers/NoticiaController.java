package br.com.gft.noticias.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.noticias.dtos.noticia.JsonConsumerNoticiaDTO;
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
    public List<JsonConsumerNoticiaDTO> buscarNoticias(@AuthenticationPrincipal Usuario usuario){
        return consumerService.buscarNoticias(usuario);
    }
}
