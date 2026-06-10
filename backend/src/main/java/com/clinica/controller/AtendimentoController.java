package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.repository.AtendimentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atendimentos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    private final AtendimentoRepository repository;

    public AtendimentoController(AtendimentoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Atendimento> criar(@Valid @RequestBody Atendimento atendimento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(atendimento));
    }

    @GetMapping
    public ResponseEntity<List<Atendimento>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Atendimento>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(repository.findByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Atendimento>> buscarPorData(@PathVariable LocalDate data) {
        return ResponseEntity.ok(repository.findByDataOrderByHorarioAsc(data));
    }

    @GetMapping("/horario/{horario}")
    public ResponseEntity<List<Atendimento>> buscarPorHorario(@PathVariable LocalTime horario) {
        return ResponseEntity.ok(repository.findByHorario(horario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Atendimento dados) {
        return repository.findById(id)
                .map(atendimento -> {
                    atendimento.setTitulo(dados.getTitulo());
                    atendimento.setData(dados.getData());
                    atendimento.setHorario(dados.getHorario());
                    atendimento.setLink(dados.getLink());
                    atendimento.setTipoReceita(dados.getTipoReceita());
                    atendimento.setReceitaDetalhada(dados.getReceitaDetalhada());
                    atendimento.setProfissionalSaude(dados.getProfissionalSaude());
                    return ResponseEntity.ok(repository.save(atendimento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(atendimento -> {
                    repository.delete(atendimento);
                    return ResponseEntity.ok(Map.of("mensagem", "Atendimento removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}