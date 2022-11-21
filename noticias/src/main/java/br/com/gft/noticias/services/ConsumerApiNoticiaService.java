package br.com.gft.noticias.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.gft.noticias.dtos.noticia.ResponseConsumerNoticia;
import br.com.gft.noticias.config.exception.EntityNotFoundException;
import br.com.gft.noticias.dtos.noticia.ListagemNoticiaDTO;
import br.com.gft.noticias.dtos.noticia.Noticia;
import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.UsuarioRepository;
import reactor.core.publisher.Mono;

@Service
public class ConsumerApiNoticiaService {

    private final WebClient webClientNoticias;
    private final UsuarioRepository usuarioRepository;
    private final HistoricoEtiquetaService historicoService;
    private final EtiquetaService etiquetaService;

    public ConsumerApiNoticiaService(WebClient webClientNoticias, UsuarioRepository usuarioRepository,
            HistoricoEtiquetaService historicoService, EtiquetaService etiquetaService) {
        this.webClientNoticias = webClientNoticias;
        this.usuarioRepository = usuarioRepository;
        this.historicoService = historicoService;
        this.etiquetaService = etiquetaService;
    }

    @Transactional
    public ListagemNoticiaDTO buscarNoticias(Usuario usuarioLogado) {
        LocalDate dataAtual = LocalDate.now();
        String data = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId()).get();

        List<Etiqueta> etiquetas = usuario.getEtiquetas();

        if (etiquetas.isEmpty()) {
            throw new EntityNotFoundException("Não há etiquetas cadastradas!");
        }

        List<Noticia> noticias = new ArrayList<>();
        
        consumerNoticiaComEtiquetas(etiquetas, data)
                .forEach(response -> noticias.addAll(filtrarData(response, dataAtual)));

        atualizarHistoricoAcessoUsuarioERankDeEtiquetas(usuario);

        return new ListagemNoticiaDTO(noticias.size(), noticias);
    }

    private List<ResponseConsumerNoticia> consumerNoticiaComEtiquetas(List<Etiqueta> etiquetas, String data) {
        List<Mono<ResponseConsumerNoticia>> monos = etiquetas.stream()
                .map(e -> this.consumerNoticia(e.getNome().replaceAll(" ", ""), data))
                .toList();

        
        return Mono.zip(monos, objects -> {
            List<ResponseConsumerNoticia> noticias = new ArrayList<>();
            for (var object : objects) {
                noticias.add( (ResponseConsumerNoticia) object);
            }
            return noticias;
        }).block();
    }

    private Mono<ResponseConsumerNoticia> consumerNoticia(String nomeEtiqueta, String data) {
        return webClientNoticias.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", nomeEtiqueta)
                        .queryParam("date", data)
                        .build())
                .retrieve()
                .bodyToMono(ResponseConsumerNoticia.class);
    }

    private List<Noticia> filtrarData(ResponseConsumerNoticia dto, LocalDate data) {
        List<Noticia> noticias = dto.list().stream()
                .filter(n -> {
                    return LocalDate.parse(n.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            .isEqual(data);
                }).toList();

        return noticias;
    }

    private void atualizarHistoricoAcessoUsuarioERankDeEtiquetas(Usuario usuario) {
        usuario.getEtiquetas().forEach(e -> {
            historicoService.cadastrarHistoricoEtiquetaUsuario(usuario, e);
            etiquetaService.atualizarAcessoEtiqueta(e);
        });
    }
}
