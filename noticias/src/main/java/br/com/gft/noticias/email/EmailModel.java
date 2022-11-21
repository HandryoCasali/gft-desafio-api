package br.com.gft.noticias.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailModel {
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
}
