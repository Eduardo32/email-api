package com.pauloeduardocosta.email.api.service;

import com.pauloeduardocosta.email.api.dto.ModeloDTO;
import com.pauloeduardocosta.email.api.dto.NovoModeloDTO;
import com.pauloeduardocosta.email.api.entity.Modelo;

public interface IModeloService {

    ModeloDTO criarModelo(NovoModeloDTO novoModeloDTO);

    Modelo buscarPorId(Long idModelo);
}
