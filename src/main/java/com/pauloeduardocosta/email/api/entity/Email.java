package com.pauloeduardocosta.email.api.entity;

import com.pauloeduardocosta.email.api.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String assunto;

    @Column(nullable = false, updatable = false)
    private String corpo;

    @Column(nullable = false, updatable = false)
    private String destinatario;

    @Column(nullable = false, updatable = false)
    private String remetente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(updatable = false)
    private String causaFalha;

    public Email(MimeMessage MimeMessage, Modelo modelo) {
        try {
            this.assunto = MimeMessage.getSubject();
            this.corpo = MimeMessage.getContent().toString();
            this.destinatario = Arrays.toString(MimeMessage.getRecipients(Message.RecipientType.TO));
            this.remetente = Arrays.toString(MimeMessage.getFrom());
            this.modelo = modelo;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PrePersist
    public void prePersist() {
        data = LocalDateTime.now();
    }
}
