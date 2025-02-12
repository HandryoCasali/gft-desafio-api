package br.com.gft.noticias.dtos.etiqueta;

import br.com.gft.noticias.entities.Etiqueta;

public record EtiquetaRankDTO(String nome, Long qntPesquisa) {
    public EtiquetaRankDTO(Etiqueta etiqueta){
        this(etiqueta.getNome(), etiqueta.getQntPesquisa());
    }
}
