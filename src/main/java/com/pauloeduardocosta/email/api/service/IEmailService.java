package com.pauloeduardocosta.email.api.service;

import com.pauloeduardocosta.email.api.dto.EmailDTO;
import com.pauloeduardocosta.email.api.dto.EmailEviadoDTO;
import org.springframework.mail.MailMessage;

public interface IEmailService {

    EmailEviadoDTO enviarEmail(EmailDTO novoEmailDTO);
}
