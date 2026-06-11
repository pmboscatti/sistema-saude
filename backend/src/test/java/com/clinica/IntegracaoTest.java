package com.clinica;

import com.clinica.model.Atendimento;
import com.clinica.model.ProfissionalSaude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveExecutarFluxoCompletoProfissionalSaude() throws Exception {
        // 1. CRIAR
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome("Dra. Ana Lima");
        profissional.setEmail("ana@clinica.com");
        profissional.setTelefone("31999990000");
        profissional.setCategoria(ProfissionalSaude.Categoria.MEDICO);

        MvcResult result = mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Dra. Ana Lima"))
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("id").asLong();

        // 2. BUSCAR
        mockMvc.perform(get("/api/profissionais/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria").value("MEDICO"));

        // 3. ATUALIZAR
        profissional.setNome("Dra. Ana Lima Silva");
        mockMvc.perform(put("/api/profissionais/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dra. Ana Lima Silva"));

        // 4. DELETAR
        mockMvc.perform(delete("/api/profissionais/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deveVincularAtendimentoAProfissional() throws Exception {
        // Criar profissional
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome("Dr. Carlos Souza");
        profissional.setCategoria(ProfissionalSaude.Categoria.FISIOTERAPEUTA);

        MvcResult profResult = mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isCreated())
                .andReturn();

        Long profId = objectMapper.readTree(profResult.getResponse().getContentAsString())
                .get("id").asLong();

        // Criar atendimento vinculado ao profissional
        String atendJson = String.format("""
                {
                    "titulo": "Sessão de fisioterapia",
                    "data": "2026-12-20",
                    "horario": "10:00",
                    "tipoReceita": "ATIVIDADE_FISICA",
                    "profissionalSaude": {"id": %d}
                }
                """, profId);

        mockMvc.perform(post("/api/atendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(atendJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Sessão de fisioterapia"));
    }
}