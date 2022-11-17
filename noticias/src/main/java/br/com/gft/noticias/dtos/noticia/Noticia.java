package br.com.gft.noticias.dtos.noticia;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Noticia {
    private String title;
    private String description;
    private String link;
    private String date;
    private String time;
}
