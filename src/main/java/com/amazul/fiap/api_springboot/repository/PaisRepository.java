package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {
    Pais findByCodigoPais(String codigo);
}
