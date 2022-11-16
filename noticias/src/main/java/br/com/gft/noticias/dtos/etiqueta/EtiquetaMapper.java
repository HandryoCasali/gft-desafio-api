package br.com.gft.noticias.dtos.etiqueta;

import br.com.gft.noticias.entities.Etiqueta;

public class EtiquetaMapper {
    public static EtiquetaDTO toEtiquetaDTO(Etiqueta etiqueta){
        return new EtiquetaDTO(etiqueta.getNome());
    };
}
