package com.oficina.agendamentosapi.repository;

import com.oficina.agendamentosapi.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {}
