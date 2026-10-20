package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Artigo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
    Page<Artigo> findAll(Pageable pageable);
}
