package br.com.gft.noticias.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
       return consumerNoticiaSemBlockACadaRequest(usuario);
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


    private List<JsonConsumerNoticiaDTO> consumerNoticiaComBlockACadaRequest(Usuario usuario) {
        LocalDate dataAtual = LocalDate.now();
        String data = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<Etiqueta> etiquetas = usuarioRepository.findById(usuario.getId()).get().getEtiquetas();

        long inicio1 = System.currentTimeMillis();

        List<JsonConsumerNoticiaDTO> listNoticiasOriginal = etiquetas.stream()
                .map(e -> this.consumerNoticia(e.getNome(), data).block())
                .toList();

        long fim1 = System.currentTimeMillis();
        System.out.println("Consumer com block levou " + (fim1 - inicio1) + " ms");

        // long inicio = System.currentTimeMillis();

        // List<JsonConsumerNoticiaDTO> listNoticias = listNoticiasOriginal.stream()
        //                                 .map(json -> filtrarData(json, dataAtual))
        //                                 .toList();

        // long fim = System.currentTimeMillis();
        // System.out.println("Filtrar data levou " + (fim - inicio) + " ms");

        return listNoticiasOriginal;
    }

    private List<JsonConsumerNoticiaDTO> consumerNoticiaSemBlockACadaRequest(Usuario usuario){
        LocalDate dataAtual = LocalDate.now();
        String data = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<Etiqueta> etiquetas = usuarioRepository.findById(usuario.getId()).get().getEtiquetas();

        long inicio1 = System.currentTimeMillis();
        List<Mono<JsonConsumerNoticiaDTO>> monos = etiquetas.stream()
                .map(e -> this.consumerNoticia(e.getNome(), data))
                .toList();

        List<JsonConsumerNoticiaDTO> json = Mono.zip(monos, objects -> {
            List<JsonConsumerNoticiaDTO> noticias = new ArrayList<>();
            for (var object : objects) {
                noticias.add((JsonConsumerNoticiaDTO) object);
            }
            return noticias;
        }).block();
        
        long fim1 = System.currentTimeMillis();
        System.out.println("Consumer sem block levou " + (fim1 - inicio1) + " ms");
        return json;
    }


    // private List<JsonConsumerNoticiaDTO> consumerComBlock() {
    //     long inicio1 = System.currentTimeMillis();

    //     JsonConsumerNoticiaDTO json1 = consumerNoticia("copa", "18/11/2022").block();
    //     JsonConsumerNoticiaDTO json2 = consumerNoticia("games", "18/11/2022").block();

    //     List<Noticia> noticias = new ArrayList<>();
    //     noticias.addAll(json1.list());
    //     noticias.addAll(json2.list());

    //     long fim1 = System.currentTimeMillis();
    //     System.out.println("Consumer levou " + (fim1 - inicio1) + " ms");
    //     return Arrays.asList(new JsonConsumerNoticiaDTO(noticias));
    // }

    // private List<JsonConsumerNoticiaDTO> consumerSemBlock() {
    //     long inicio1 = System.currentTimeMillis();
    //     Mono<JsonConsumerNoticiaDTO> mono1 = consumerNoticia("copa", "18/11/2022");
    //     Mono<JsonConsumerNoticiaDTO> mono2 = consumerNoticia("games", "18/11/2022");
    //     JsonConsumerNoticiaDTO json = Mono.zip(mono1, mono2).map((t) -> {
    //         List<Noticia> noticias = new ArrayList<>();
    //         noticias.addAll((((JsonConsumerNoticiaDTO) t.get(0))).list());
    //         noticias.addAll((((JsonConsumerNoticiaDTO) t.get(1))).list());
    //         return new JsonConsumerNoticiaDTO(noticias);
    //     }).block();

    //     long fim1 = System.currentTimeMillis();
    //     System.out.println("Consumer levou " + (fim1 - inicio1) + " ms");
    //     return Arrays.asList(json);
    // }

    
}
