package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.*;

@Entity
public class Artigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Local local;

    private String titulo;
    private String resumo;

    @Lob
    private String corpo;

    @Enumerated(EnumType.STRING)
    private AreaAplicacao areaAplicacao;

    @Enumerated(EnumType.STRING)
    private StatusArtigo status;
}
