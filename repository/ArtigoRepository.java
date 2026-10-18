package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
}
