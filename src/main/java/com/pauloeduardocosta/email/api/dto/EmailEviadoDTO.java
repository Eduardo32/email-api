package com.pauloeduardocosta.email.api.dto;

import com.pauloeduardocosta.email.api.entity.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class EmailEviadoDTO {

    private String de;
    private String para;
    private String assunto;
    private String corpo;


    public EmailEviadoDTO(Email email) {
        this.de = email.getRemetente();
        this.para = email.getDestinatario();
        this.assunto = email.getAssunto();
        this.corpo = email.getCorpo();
    }
}
