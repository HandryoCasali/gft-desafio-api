package br.com.gft.noticias.dtos.usuario;

import java.util.List;

import br.com.gft.noticias.dtos.etiqueta.EtiquetaDTO;

public record DetalhesUsuarioDTO(Long id, String nome, String email, String perfil, List<EtiquetaDTO> etiquetas) {
    
}
