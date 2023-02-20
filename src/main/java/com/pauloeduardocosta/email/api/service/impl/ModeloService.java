package com.pauloeduardocosta.email.api.service.impl;

import com.pauloeduardocosta.email.api.dto.ModeloDTO;
import com.pauloeduardocosta.email.api.dto.NovoModeloDTO;
import com.pauloeduardocosta.email.api.entity.DescricaoParametro;
import com.pauloeduardocosta.email.api.entity.Modelo;
import com.pauloeduardocosta.email.api.enums.ETipoCorpo;
import com.pauloeduardocosta.email.api.repository.IModeloRepository;
import com.pauloeduardocosta.email.api.service.IModeloService;
import com.pauloeduardocosta.email.api.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ModeloService implements IModeloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModeloService.class);

    @Autowired
    private IModeloRepository modeloRepository;

    private final String PADRAO_VARIAVEIS_CORPO_EMAIL = "\\{[0-9]{1,99}\\}";

    @Override
    @Transactional
    public ModeloDTO criarModelo(NovoModeloDTO novoModeloDTO) {
        LOGGER.info("Tentando salvar um novo modelo de email {}", novoModeloDTO);
        verificarDescricaoParametros(novoModeloDTO.getCorpo(), novoModeloDTO.getDescricaoParametros());
        Modelo modelo = Modelo.builder()
                .descricao(novoModeloDTO.getDescricao())
                .assunto(novoModeloDTO.getAssunto())
                .corpo(novoModeloDTO.getCorpo())
                .tipoCorpo(ETipoCorpo.valueOf(novoModeloDTO.getTipoCorpo()))
                .build();
        List<DescricaoParametro> descricoes = montarDescricoes(novoModeloDTO.getDescricaoParametros(), modelo);
        modelo.setDescricaoParametros(descricoes);
        modeloRepository.save(modelo);
        LOGGER.info("Novo modelo de email salvo {}", modelo);
        return new ModeloDTO(modelo);
    }

    @Override
    public Modelo buscarPorId(Long idModelo) {
        Optional<Modelo> modelo = modeloRepository.findById(idModelo);
        if(modelo.isEmpty()) {
            throw new IllegalArgumentException("modelo inexistente");
        }
        return modelo.get();
    }

    private void verificarDescricaoParametros(String modeloCorpo, List<String> descricoes) {
        LOGGER.info("Verificando descrição para os parametros do modelo de email");
        int qtdEsperadaParametros = StringUtils.quantidadeDeOcorencias(modeloCorpo, PADRAO_VARIAVEIS_CORPO_EMAIL);
        if(qtdEsperadaParametros != descricoes.size()) {
            LOGGER.error("Número de descrições {} incompatível com o número de parametros no corpo do modelo {}", descricoes.size(), modeloCorpo);
            throw new IllegalArgumentException();
        }
    }

    private List<DescricaoParametro> montarDescricoes(List<String> descricaoParametros, Modelo modelo) {
        LOGGER.info("Organizando descrições dos parametros do modelo de email {}", descricaoParametros);
        AtomicInteger parametroIndece = new AtomicInteger();
        return descricaoParametros.stream().map(descricao ->
            DescricaoParametro.builder()
                    .modelo(modelo)
                    .indice(parametroIndece.getAndIncrement())
                    .descricao(descricao)
                    .build()
        ).collect(Collectors.toList());
    }
}
