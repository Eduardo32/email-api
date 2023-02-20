package com.pauloeduardocosta.email.api.v1.rs;

import com.pauloeduardocosta.email.api.dto.EmailDTO;
import com.pauloeduardocosta.email.api.dto.EmailEviadoDTO;
import com.pauloeduardocosta.email.api.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/email")
public class EmailV1RS {

    @Autowired
    private IEmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity enviarEmail(@Validated @RequestBody EmailDTO emailDTO) {
        EmailEviadoDTO emailEviadoDTO = emailService.enviarEmail(emailDTO);
        return ResponseEntity.ok(emailEviadoDTO);
    }
}
