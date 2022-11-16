package br.com.gft.noticias.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.noticias.config.security.auth.AutenticacaoDTO;
import br.com.gft.noticias.config.security.auth.TokenDTO;
import br.com.gft.noticias.services.TokenService;



@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

    private final TokenService autenticacaoService;

    public AutenticacaoController(TokenService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authForm) {
        try {
            return ResponseEntity.ok(autenticacaoService.autenticar(authForm));
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
