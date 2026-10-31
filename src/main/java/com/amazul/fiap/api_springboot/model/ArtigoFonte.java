package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "artigo_fonte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"artigo_id", "fonte_id"})
})
public class ArtigoFonte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artigo_id")
    private Artigo artigo;

    @ManyToOne
    @JoinColumn(name = "fonte_id")
    private Fonte fonte;

    @Column(length = 500)
    private String observacao;

}

//    @Column(name = "artigo_id")
//    private Long artigoId;
//
//    @Column(name = "fonte_id")
//    private Long fonteId;
