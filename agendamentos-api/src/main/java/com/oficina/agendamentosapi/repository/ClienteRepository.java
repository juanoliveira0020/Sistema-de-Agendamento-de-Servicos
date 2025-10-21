package com.oficina.agendamentosapi.repository;

import com.oficina.agendamentosapi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
