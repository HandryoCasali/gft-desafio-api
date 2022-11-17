package br.com.gft.noticias.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etiquetas")
@Data
@NoArgsConstructor
public class Etiqueta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private Long qntPesquisa = 0l;

    @ManyToMany
    @JoinTable(name = "usuarios_etiquetas",
    joinColumns = @JoinColumn(name="usuario_id"),
    inverseJoinColumns = @JoinColumn(name="etiqueta_id"))
    private List<Usuario> usuarios = new ArrayList<>();


}
