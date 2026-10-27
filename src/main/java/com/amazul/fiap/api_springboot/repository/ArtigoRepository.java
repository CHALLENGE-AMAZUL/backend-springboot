package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Artigo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
    Page<Artigo> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Artigo> findByLocal_NomePais(String localNomePais, Pageable pageable);
}
