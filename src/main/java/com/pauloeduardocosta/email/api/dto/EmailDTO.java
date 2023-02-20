package com.pauloeduardocosta.email.api.dto;

import com.pauloeduardocosta.email.api.vo.ContatoVO;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class EmailDTO {

    private Long idModelo;
    private ContatoVO destinatario;
    private ContatoVO remetente;
    private List<String> parametros;
}
