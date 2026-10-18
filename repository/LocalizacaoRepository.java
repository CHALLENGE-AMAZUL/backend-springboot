package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
}
