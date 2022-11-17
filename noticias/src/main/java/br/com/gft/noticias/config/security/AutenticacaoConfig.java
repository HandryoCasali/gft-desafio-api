package br.com.gft.noticias.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.gft.noticias.filters.FiltroAutenticacao;
import br.com.gft.noticias.services.TokenService;
import br.com.gft.noticias.services.UsuarioService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@EnableSwagger2
public class AutenticacaoConfig {
    
    private final TokenService autenticacaoService;

    private final UsuarioService usuarioService;

    public AutenticacaoConfig(TokenService autenticacaoService, UsuarioService usuarioService) {
        this.autenticacaoService = autenticacaoService;
        this.usuarioService = usuarioService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**","/configuration/**", "/swagger-resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new FiltroAutenticacao(autenticacaoService, usuarioService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}
