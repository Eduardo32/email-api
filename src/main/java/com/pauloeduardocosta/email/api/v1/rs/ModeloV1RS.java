package com.pauloeduardocosta.email.api.v1.rs;

import com.pauloeduardocosta.email.api.dto.ModeloDTO;
import com.pauloeduardocosta.email.api.dto.NovoModeloDTO;
import com.pauloeduardocosta.email.api.service.IModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("v1/modelo")
public class ModeloV1RS {

    @Autowired
    private IModeloService modeloService;

    @PostMapping
    public ResponseEntity criarModelo(@Validated @RequestBody NovoModeloDTO novoModeloDTO,
                                      UriComponentsBuilder uriBuilder) {
        ModeloDTO modeloDTO = modeloService.criarModelo(novoModeloDTO);
        return ResponseEntity.created(null).body(modeloDTO);
    }
}
