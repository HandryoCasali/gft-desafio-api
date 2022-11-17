package br.com.gft.noticias.controllers;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.noticias.dtos.historico.HistoricoDTO;
import br.com.gft.noticias.dtos.usuario.DetalhesUsuarioDTO;
import br.com.gft.noticias.dtos.usuario.UsuarioAtualizacao;
import br.com.gft.noticias.dtos.usuario.UsuarioDTO;
import br.com.gft.noticias.dtos.usuario.UsuarioForm;
import br.com.gft.noticias.dtos.usuario.UsuarioMapper;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.services.HistoricoEtiquetaService;
import br.com.gft.noticias.services.UsuarioService;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final HistoricoEtiquetaService historicoService;

    public UsuarioController(UsuarioService usuarioService, HistoricoEtiquetaService historicoService) {
        this.usuarioService = usuarioService;
        this.historicoService = historicoService;
    }

    
    @GetMapping
    @RolesAllowed("ADMIN")
    public Page<UsuarioDTO> listarUsuariosCadastrados(Pageable pageable) {
        return usuarioService.listar(pageable).map(UsuarioMapper::toUsuarioDTO);
    }


    @GetMapping("historico")
    @RolesAllowed("USER")
    public Page<HistoricoDTO> historicoUsuario(@AuthenticationPrincipal Usuario usuario,
            @PageableDefault(sort = "idDataAcesso", direction = Direction.DESC) Pageable pageable) {
        return historicoService.historicoEtiquetaDoUsuario(usuario.getId(), pageable).map(HistoricoDTO::new);
    }

    @GetMapping("historico/{id}")
    @RolesAllowed("ADMIN")
    public Page<HistoricoDTO> historicoUsuario(@PathVariable Long id, @PageableDefault(sort = "idDataAcesso", direction = Direction.DESC) Pageable pageable) {
        return historicoService.historicoEtiquetaDoUsuario(id, pageable).map(HistoricoDTO::new);
    }

    @GetMapping("detalhes")
    public ResponseEntity<DetalhesUsuarioDTO> buscarUsuarioLogado(@AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(UsuarioMapper.toDetalhesUsuario(usuarioService.buscarUsuarioPorId(usuario.getId())));
    }

    @GetMapping("detalhes/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<DetalhesUsuarioDTO> buscarUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioMapper.toDetalhesUsuario(usuarioService.buscarUsuarioPorId(id)));
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioForm form) {
        return ResponseEntity.ok(UsuarioMapper.toUsuarioDTO(usuarioService.cadastrar(form)));
    }

    @PutMapping("nova-senha")
    public void atualizarSenha(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid UsuarioAtualizacao form) {
        usuarioService.atualizarSenha(usuario, form);
    }

    @DeleteMapping("{id}")
    public void excluirUsuario(@AuthenticationPrincipal Usuario usuario) {
        usuarioService.excluir(usuario.getId());
    }

}
