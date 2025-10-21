package com.oficina.agendamentosapi.controller;

import com.oficina.agendamentosapi.model.Agendamento;
import com.oficina.agendamentosapi.model.Cliente;
import com.oficina.agendamentosapi.model.Servico;
import com.oficina.agendamentosapi.repository.AgendamentoRepository;
import com.oficina.agendamentosapi.repository.ClienteRepository;
import com.oficina.agendamentosapi.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoController(AgendamentoRepository repository, ClienteRepository clienteRepository, ServicoRepository servicoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Agendamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Agendamento> buscar(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Agendamento criar(@RequestBody Agendamento agendamento) {
        // Validação de entrada
        if (agendamento.getCliente() == null || agendamento.getCliente().getId() == null) {
            throw new RuntimeException("Cliente não informado");
        }
        if (agendamento.getServico() == null || agendamento.getServico().getId() == null) {
            throw new RuntimeException("Serviço não informado");
        }

        // Busca Cliente e Serviço no banco
        Cliente cliente = clienteRepository.findById(agendamento.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Servico servico = servicoRepository.findById(agendamento.getServico().getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        return repository.save(agendamento);
    }

    @PutMapping("/{id}")
    public Agendamento atualizar(@PathVariable Long id, @RequestBody Agendamento dados) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setDataAgendada(dados.getDataAgendada());
        agendamento.setStatus(dados.getStatus());
        agendamento.setObservacoes(dados.getObservacoes());

        // Atualiza Cliente e Serviço se forem fornecidos
        if (dados.getCliente() != null && dados.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(dados.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            agendamento.setCliente(cliente);
        }

        if (dados.getServico() != null && dados.getServico().getId() != null) {
            Servico servico = servicoRepository.findById(dados.getServico().getId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            agendamento.setServico(servico);
        }

        return repository.save(agendamento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        repository.deleteById(id);
    }
}
