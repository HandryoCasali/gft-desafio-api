package br.com.gft.noticias.dtos.noticia;

import java.util.List;

public record ListagemNoticiaDTO(int count, List<Noticia> noticias) {
    
}
