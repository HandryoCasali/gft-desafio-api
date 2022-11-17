package br.com.gft.noticias.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.gft.noticias.dtos.noticia.JsonConsumerNoticiaDTO;
import br.com.gft.noticias.dtos.noticia.Noticia;
import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import br.com.gft.noticias.repositories.UsuarioRepository;
import reactor.core.publisher.Mono;

@Service
public class ConsumerApiNoticiaService {

    private final WebClient webClientNoticias;
    private final UsuarioRepository usuarioRepository;

    public ConsumerApiNoticiaService(WebClient webClientNoticias, UsuarioRepository usuarioRepository) {
        this.webClientNoticias = webClientNoticias;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public List<JsonConsumerNoticiaDTO> buscarNoticias(Usuario usuario) {
        LocalDate dataAtual = LocalDate.now();
        String data = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<Etiqueta> etiquetas = usuarioRepository.findById(usuario.getId()).get().getEtiquetas();

        List<JsonConsumerNoticiaDTO> listNoticiasOriginal = etiquetas.stream()
                .map(e -> this.consumerNoticia(e.getNome(), data).block())
                .toList();

        List<JsonConsumerNoticiaDTO> listNoticias = listNoticiasOriginal.stream().map(json -> filtrarData(json, dataAtual)).toList();
        return listNoticias;
    }

    private JsonConsumerNoticiaDTO filtrarData(JsonConsumerNoticiaDTO dto, LocalDate data) {
        List<Noticia> noticias = dto.list().stream()
                .filter(n -> {
                    return LocalDate.parse(n.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            .isEqual(data);
                }).toList();

        return new JsonConsumerNoticiaDTO(noticias);
    }

    private Mono<JsonConsumerNoticiaDTO> consumerNoticia(String nomeEtiqueta, String data) {
        return webClientNoticias.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", nomeEtiqueta)
                        .queryParam("date", data)
                        .build())
                .retrieve()
                .bodyToMono(JsonConsumerNoticiaDTO.class);
    }
}
