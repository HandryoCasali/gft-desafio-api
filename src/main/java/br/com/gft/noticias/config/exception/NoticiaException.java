package br.com.gft.noticias.config.exception;

import lombok.Getter;

@Getter
public class NoticiaException extends RuntimeException {
    private String message;
    public NoticiaException(String message) {
        super(message);
        this.message = message;
    }
}
