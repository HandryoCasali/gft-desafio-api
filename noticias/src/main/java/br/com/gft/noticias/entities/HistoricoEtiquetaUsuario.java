package br.com.gft.noticias.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.gft.noticias.entities.pk.HistoricoEtiquetaUsuarioPK;
import lombok.Data;

@Data
@Entity
@Table(name = "historicos_etiquetas_usuarios")
public class HistoricoEtiquetaUsuario {
    
    @EmbeddedId
    private HistoricoEtiquetaUsuarioPK id;

}
