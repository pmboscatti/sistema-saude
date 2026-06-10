package com.clinica;

import com.clinica.controller.AtendimentoController;
import com.clinica.model.Atendimento;
import com.clinica.repository.AtendimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AtendimentoController.class)
class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtendimentoRepository repository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveCriarAtendimentoComSucesso() throws Exception {
        Atendimento atend = new Atendimento();
        atend.setId(1L);
        atend.setTitulo("Consulta Psicológica");
        atend.setData(LocalDate.of(2026, 12, 15));
        atend.setHorario(LocalTime.of(14, 0));
        atend.setTipoReceita(Atendimento.TipoReceita.ATIVIDADE_MENTAL);
        atend.setReceitaDetalhada("Exercício de respiração diário.");

        when(repository.save(any(Atendimento.class))).thenReturn(atend);

        mockMvc.perform(post("/api/atendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atend)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Consulta Psicológica"))
                .andExpect(jsonPath("$.tipoReceita").value("ATIVIDADE_MENTAL"));
    }

    @Test
    void deveListarAtendimentos() throws Exception {
        Atendimento a1 = new Atendimento();
        a1.setId(1L);
        a1.setTitulo("Consulta Geral");
        a1.setData(LocalDate.of(2026, 12, 15));
        a1.setHorario(LocalTime.of(10, 0));

        Atendimento a2 = new Atendimento();
        a2.setId(2L);
        a2.setTitulo("Sessão Fisioterapia");
        a2.setData(LocalDate.of(2026, 12, 15));
        a2.setHorario(LocalTime.of(11, 0));

        when(repository.findAll()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/atendimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Consulta Geral"))
                .andExpect(jsonPath("$[1].titulo").value("Sessão Fisioterapia"));
    }

    @Test
    void deveRetornar404ParaAtendimentoInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/atendimentos/999"))
                .andExpect(status().isNotFound());
    }
}