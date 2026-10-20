package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ArtigoFonte {
    @ManyToOne
    @JoinColumn(name = "artigo_id")
    private Artigo artigo;

    @ManyToOne
    @JoinColumn(name = "fonte_id")
    private Fonte fonte;

    private String observacao;

}

//    @Column(name = "artigo_id")
//    private Long artigoId;
//
//    @Column(name = "fonte_id")
//    private Long fonteId;
