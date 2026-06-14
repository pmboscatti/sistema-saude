package com.clinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "exames_laboratorio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExameLaboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(length = 500, nullable = false)
    private String descricao;

    @NotBlank(message = "Posologia é obrigatória")
    @Column(length = 500, nullable = false)
    private String posologia;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}