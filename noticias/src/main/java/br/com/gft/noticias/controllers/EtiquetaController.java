package br.com.gft.noticias.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.gft.noticias.dtos.etiqueta.EtiquetaForm;
import br.com.gft.noticias.dtos.etiqueta.EtiquetaRankDTO;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.services.EtiquetaService;

@RestController
@RequestMapping("v1/etiquetas")
public class EtiquetaController {
    private final EtiquetaService etiquetaService;

    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public Page<EtiquetaRankDTO> listarEtiquetasRank(@PageableDefault(sort = "qntPesquisa", direction = Direction.DESC) Pageable pageable){
        return etiquetaService.listarEtiquetas(pageable).map(EtiquetaRankDTO::new);
    }

    @PostMapping
    @RolesAllowed("USER")
    public ResponseEntity<?> cadastrarEtiqueta( @AuthenticationPrincipal Usuario usuario, @RequestBody EtiquetaForm form){
        etiquetaService.cadastrarEtiquetaNoUsuarioLogado(usuario, form.nome());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @RolesAllowed("USER")
    public void deletarEtiqueta( @AuthenticationPrincipal Usuario usuario, @RequestBody EtiquetaForm form){
       etiquetaService.deletarEtiquetaNoUsuarioLogado(usuario, form.nome().trim());
    }
    
}
