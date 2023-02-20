package com.pauloeduardocosta.email.api.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class NovoModeloDTO {

    private String descricao;
    private String assunto;
    private String corpo;
    private List<String> descricaoParametros;
    private String tipoCorpo;
}
