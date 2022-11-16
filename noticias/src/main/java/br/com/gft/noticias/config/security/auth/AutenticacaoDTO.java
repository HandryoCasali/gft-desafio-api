package br.com.gft.noticias.config.security.auth;

import javax.validation.constraints.NotBlank;

public record AutenticacaoDTO(@NotBlank String email, @NotBlank String senha){

}
