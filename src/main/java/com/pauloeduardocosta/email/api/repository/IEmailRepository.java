package com.pauloeduardocosta.email.api.repository;

import com.pauloeduardocosta.email.api.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailRepository extends JpaRepository<Email, Long> {
}
