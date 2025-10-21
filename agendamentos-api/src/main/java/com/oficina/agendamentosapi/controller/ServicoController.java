package com.oficina.agendamentosapi.controller;

import com.oficina.agendamentosapi.model.Servico;
import com.oficina.agendamentosapi.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoRepository repository;

    public ServicoController(ServicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Servico> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Servico criar(@RequestBody Servico servico) {
        return repository.save(servico);
    }
}
