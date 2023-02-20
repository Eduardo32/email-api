package com.pauloeduardocosta.email.api.vo;

import lombok.Getter;

@Getter
public class ContatoVO {

    private String nome;
    private String email;

    public String getContato() {
        return nome + " <" + email + ">";
    }
}
