package com.oficina.agendamentosapi.repository;

import com.oficina.agendamentosapi.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {}
