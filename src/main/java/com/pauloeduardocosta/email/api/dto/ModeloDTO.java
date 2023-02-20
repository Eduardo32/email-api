package com.pauloeduardocosta.email.api.dto;

import com.pauloeduardocosta.email.api.entity.Modelo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ModeloDTO {

    private Long id;
    private String descricao;
    private String assunto;
    private String corpo;
    private List<DescricaoParametroDTO> descricaoParametros;
    private String tipoCorpo;

    public ModeloDTO(Modelo modelo) {
        this.id = modelo.getId();
        this.descricao = modelo.getDescricao();
        this.assunto = modelo.getAssunto();
        this.corpo = modelo.getCorpo();
        this.descricaoParametros = DescricaoParametroDTO.montarDTO(modelo.getDescricaoParametros());
        this.tipoCorpo = modelo.getTipoCorpo().name();
    }
}
