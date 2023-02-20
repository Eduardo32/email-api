package com.pauloeduardocosta.email.api.service.impl;

import com.pauloeduardocosta.email.api.dto.EmailDTO;
import com.pauloeduardocosta.email.api.dto.EmailEviadoDTO;
import com.pauloeduardocosta.email.api.entity.Email;
import com.pauloeduardocosta.email.api.entity.Modelo;
import com.pauloeduardocosta.email.api.enums.EStatus;
import com.pauloeduardocosta.email.api.enums.ETipoCorpo;
import com.pauloeduardocosta.email.api.repository.IEmailRepository;
import com.pauloeduardocosta.email.api.service.IEmailService;
import com.pauloeduardocosta.email.api.service.IModeloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EmailService implements IEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private Environment env;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IModeloService modeloService;

    @Autowired
    private IEmailRepository emailRepository;

    @Override
    public EmailEviadoDTO enviarEmail(EmailDTO emailDTO) {
        LOGGER.info("Tentando enviar email {}", emailDTO);
        Modelo modelo = modeloService.buscarPorId(emailDTO.getIdModelo());
        if(modelo.getDescricaoParametros().size() != emailDTO.getParametros().size()) {
            throw new IllegalArgumentException("Quantidade incorreta de parametros");
        }
        String corpo = montarCorpo(modelo.getCorpo(), emailDTO.getParametros());
        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email);
        try {
            helper.setSubject(modelo.getAssunto());
            helper.setTo(emailDTO.getDestinatario().getContato());
            helper.setFrom(emailDTO.getRemetente().getContato());
            helper.setText(corpo, ETipoCorpo.HTML.equals(modelo.getTipoCorpo()));
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
        return new EmailEviadoDTO(enviar(email, modelo));
    }
    
    private Email enviar(MimeMessage email, Modelo modelo) {
        Email emailEnviado = new Email(email, modelo);
        try {
            mailSender.send(email);
            emailEnviado.setStatus(EStatus.SUCESSO);
            emailRepository.save(emailEnviado);
            return emailEnviado;
        } catch (MailException ex) {
            emailEnviado.setStatus(EStatus.FALHA);
            emailEnviado.setCausaFalha(ex.getCause().getMessage());
            emailRepository.save(emailEnviado);
            throw new RuntimeException("Por favor, tente novamente mais tarde.");
        }
    }

    private String montarCorpo(String modeloCorpo, List<String> parametros) {
        AtomicReference<String> mensagem = new AtomicReference<>(modeloCorpo);
        AtomicInteger parametroIndece = new AtomicInteger();
        parametros.forEach(parametro -> {
            mensagem.set(mensagem.get().replace("{" + parametroIndece.getAndIncrement() + "}", parametro));
        });
        return mensagem.get();
    }
}
