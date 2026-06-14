package com.clinica.repository;

import com.clinica.model.ExameLaboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExameLaboratorioRepository extends JpaRepository<ExameLaboratorio, Long> {

    List<ExameLaboratorio> findByDescricaoContainingIgnoreCase(String descricao);

    List<ExameLaboratorio> findByPosologiaContainingIgnoreCase(String posologia);
}