package br.com.gft.noticias.dtos.etiqueta;

import javax.validation.constraints.NotBlank;

public record EtiquetaForm(@NotBlank String nome) {
    
}
