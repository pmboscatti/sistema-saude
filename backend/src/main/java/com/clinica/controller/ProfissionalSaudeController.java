package com.clinica.controller;

import com.clinica.model.ProfissionalSaude;
import com.clinica.repository.ProfissionalSaudeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeRepository repository;

    public ProfissionalSaudeController(ProfissionalSaudeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaude> criar(@Valid @RequestBody ProfissionalSaude profissional) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(profissional));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listar() {
        return ResponseEntity.ok(repository.findAllByOrderByNomeAsc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(repository.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProfissionalSaude>> buscarPorCategoria(@PathVariable ProfissionalSaude.Categoria categoria) {
        return ResponseEntity.ok(repository.findByCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody ProfissionalSaude dados) {
        return repository.findById(id)
                .map(profissional -> {
                    profissional.setNome(dados.getNome());
                    profissional.setEmail(dados.getEmail());
                    profissional.setTelefone(dados.getTelefone());
                    profissional.setEndereco(dados.getEndereco());
                    profissional.setCategoria(dados.getCategoria());
                    return ResponseEntity.ok(repository.save(profissional));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(profissional -> {
                    repository.delete(profissional);
                    return ResponseEntity.ok(Map.of("mensagem", "Profissional removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}