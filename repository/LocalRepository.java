package com.amazul.fiap.api_springboot.repository;

import com.amazul.fiap.api_springboot.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}
