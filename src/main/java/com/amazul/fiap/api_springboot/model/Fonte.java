package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_fonte")
public class Fonte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode ser vazio!")
    @Column(length = 150, nullable = false)
    private String titulo;

    @NotBlank(message = "URL deve ser inserida.")
    @Column(unique = true, nullable = false, length = 500)
    private String url;

    @OneToMany(mappedBy = "fonte")
    List<ArtigoFonte> artigos;
    private LocalDate dataConsulta;

    private Date atualizadoEm;
}
