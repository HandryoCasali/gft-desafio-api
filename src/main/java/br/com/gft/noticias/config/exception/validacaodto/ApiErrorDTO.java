package br.com.gft.noticias.config.exception.validacaodto;

import org.springframework.http.HttpStatus;

public record ApiErrorDTO(HttpStatus status, String message){
}
