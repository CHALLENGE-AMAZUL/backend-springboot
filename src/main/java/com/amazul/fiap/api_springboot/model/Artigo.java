package com.amazul.fiap.api_springboot.model;

import com.amazul.fiap.api_springboot.exception.TransicaoInvalidaStatusException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Artigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AreaAplicacao areaAplicacao;

    private String titulo;
    private String resumo;

    private String aplicacaoReal;
    private String impactoSociedade;
    //Antigo 'corpo' era @Lob
    //Pensando em desmembrar corpo em Problema, AplicacaoReal - no cotidiano, sociedade, e beneficio.

    @ManyToOne
    private Local local;

    @Enumerated(EnumType.STRING)
    private StatusArtigo status = StatusArtigo.RASCUNHO;

    @OneToMany(
            mappedBy = "artigo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ArtigoFonte> referencias = new ArrayList<>();

    public void setStatus(StatusArtigo status) {
        if(this.status == null && status != StatusArtigo.RASCUNHO){
            throw new TransicaoInvalidaStatusException("Status inicial do artigo deve ser RASCUNHO");
        } else if(this.status == StatusArtigo.PUBLICADO || this.status == StatusArtigo.ARQUIVADO){
            throw new TransicaoInvalidaStatusException("Esse artigo não pode ser alterado para: " +  status);
        }
        else{
            this.status = status;
        }
    }
}
