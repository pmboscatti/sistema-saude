package com.clinica.controller;

import com.clinica.model.ExameLaboratorio;
import com.clinica.repository.ExameLaboratorioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exames-laboratorio")
@CrossOrigin(origins = "*")
public class ExameLaboratorioController {

    private final ExameLaboratorioRepository repository;

    public ExameLaboratorioController(ExameLaboratorioRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ExameLaboratorio> criar(
            @Valid @RequestBody ExameLaboratorio exame) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(exame));
    }

    @GetMapping
    public ResponseEntity<List<ExameLaboratorio>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ExameLaboratorio>> buscarPorDescricao(
            @RequestParam String descricao) {

        return ResponseEntity.ok(
                repository.findByDescricaoContainingIgnoreCase(descricao)
        );
    }

    @GetMapping("/posologia")
    public ResponseEntity<List<ExameLaboratorio>> buscarPorPosologia(
            @RequestParam String posologia) {

        return ResponseEntity.ok(
                repository.findByPosologiaContainingIgnoreCase(posologia)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ExameLaboratorio dados) {

        return repository.findById(id)
                .map(exame -> {
                    exame.setDescricao(dados.getDescricao());
                    exame.setPosologia(dados.getPosologia());

                    return ResponseEntity.ok(repository.save(exame));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        return repository.findById(id)
                .map(exame -> {
                    repository.delete(exame);

                    return ResponseEntity.ok(
                            Map.of("mensagem",
                                    "Exame removido com sucesso")
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }
}