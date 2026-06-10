package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Column(length = 200, nullable = false)
    private String titulo;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Horário é obrigatório")
    private LocalTime horario;
    
    @Column(length = 500)
    private String link;

    public enum TipoReceita {
        REMEDIO, ATIVIDADE_FISICA, ATIVIDADE_MENTAL
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_receita", length = 20)
    private TipoReceita tipoReceita;

    @Column(columnDefinition = "TEXT")
    private String receitaDetalhada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionalSaude profissionalSaude;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}