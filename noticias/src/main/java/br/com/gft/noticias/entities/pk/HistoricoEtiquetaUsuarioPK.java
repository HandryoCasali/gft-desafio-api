package br.com.gft.noticias.entities.pk;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import br.com.gft.noticias.entities.Etiqueta;
import br.com.gft.noticias.entities.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class HistoricoEtiquetaUsuarioPK implements Serializable {

    @ManyToOne
    private Usuario usuario;
    
    @ManyToOne
    private Etiqueta etiqueta;

    @Column(name = "data_acesso")
    private LocalDateTime dataAcesso = LocalDateTime.now();

    public HistoricoEtiquetaUsuarioPK(Usuario usuario, Etiqueta etiqueta) {
        this.usuario = usuario;
        this.etiqueta = etiqueta;
    }
}
