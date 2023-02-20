package com.pauloeduardocosta.email.api.dto;

import com.pauloeduardocosta.email.api.entity.DescricaoParametro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@ToString
public class DescricaoParametroDTO {
    
    private Integer indice;
    private String descricao;

    public DescricaoParametroDTO(DescricaoParametro descricaoParametro) {
        this.indice = descricaoParametro.getIndice();
        this.descricao = descricaoParametro.getDescricao();
    }

    public static List<DescricaoParametroDTO> montarDTO(List<DescricaoParametro> descricaoParametros) {
        return descricaoParametros.stream().map(DescricaoParametroDTO::new).collect(Collectors.toList());
    }
}
