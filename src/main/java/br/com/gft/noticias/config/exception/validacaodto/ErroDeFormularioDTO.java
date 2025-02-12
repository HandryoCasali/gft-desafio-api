package br.com.gft.noticias.config.exception.validacaodto;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public record ErroDeFormularioDTO(HttpStatus status, String message, List<String> erros ){

    public ErroDeFormularioDTO(HttpStatus status, String message, String error){
        this(status, message, Arrays.asList(error));
    }
    
}
