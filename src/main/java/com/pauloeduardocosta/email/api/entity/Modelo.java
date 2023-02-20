package com.pauloeduardocosta.email.api.entity;

import com.pauloeduardocosta.email.api.enums.ETipoCorpo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String assunto;

    @Column(nullable = false)
    private String corpo;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private ETipoCorpo tipoCorpo;

    @OneToMany(mappedBy = "modelo",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<DescricaoParametro> descricaoParametros;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime atual = LocalDateTime.now();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
}
