package br.com.gft.noticias.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {
    
    @GetMapping
    public String hello(){
        return "hello world";
    }
}
