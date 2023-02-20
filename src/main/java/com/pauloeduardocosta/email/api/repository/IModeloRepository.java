package com.pauloeduardocosta.email.api.repository;

import com.pauloeduardocosta.email.api.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModeloRepository extends JpaRepository<Modelo, Long> {
}
