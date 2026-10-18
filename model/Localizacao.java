package com.amazul.fiap.api_springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Dúvida sobre nome - por conta da 3FN

    private String codigoPais;
    private Double latitude;
    private Double longitude;
}
