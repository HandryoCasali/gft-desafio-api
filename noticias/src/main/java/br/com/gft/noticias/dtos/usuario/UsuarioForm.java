package br.com.gft.noticias.dtos.usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UsuarioForm(
        @NotBlank 
        String nome,

        @Email @NotNull 
        String email,

        @NotBlank @Size(min = 8, max = 15)
        String senha,

        @NotBlank 
        String perfil) {

}
