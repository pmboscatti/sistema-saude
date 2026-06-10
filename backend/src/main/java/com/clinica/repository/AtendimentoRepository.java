package com.clinica.repository;

import com.clinica.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByTituloContainingIgnoreCase(String titulo);

    List<Atendimento> findByDataOrderByHorarioAsc(LocalDate data);

    List<Atendimento> findByHorario(LocalTime horario);
}