package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.*;

@Entity
public class Artigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AreaAplicacao areaAplicacao;

    private String titulo;
    private String resumo;

    @Lob
    private String corpo;

    @ManyToOne
    private Local local;

    @Enumerated(EnumType.STRING)
    private StatusArtigo status;
}
