package br.com.gft.noticias.dtos.usuario;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UsuarioAtualizacao(@NotBlank @Size(min = 8, max = 15) String novaSenha) {
}
