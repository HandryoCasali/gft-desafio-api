package br.com.gft.noticias.dtos.historico;

import java.time.LocalDateTime;

import br.com.gft.noticias.entities.HistoricoEtiquetaUsuario;

public record HistoricoDTO(String etiqueta, LocalDateTime dataAcesso) {
    public HistoricoDTO(HistoricoEtiquetaUsuario historico){
        this(historico.getId().getEtiqueta().getNome(),
            historico.getId().getDataAcesso());
    }
}
