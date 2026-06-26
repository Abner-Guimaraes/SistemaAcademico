package org.example.service;

import org.example.model.Turma;
import org.example.model.Prova;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoRelatorioTeste {

    @Test
    @DisplayName("AC4 - Deve gerar relatório sem erros quando não há turmas registradas")
    void deveGerarRelatorioSemTurmas() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        String relatorio = servico.gerarResumoAvaliacoes(turmas, "ADMIN");
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("Nenhuma turma registrada"));
    }

    @Test
    @DisplayName("AC1, AC3 - Deve listar turma mesmo sem avaliações")
    void deveListarTurmaSemAvaliacao() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        turmas.add(new Turma("CC3A", "POO"));
        
        String relatorio = servico.gerarResumoAvaliacoes(turmas, "ADMIN");
        assertTrue(relatorio.contains("CC3A"));
        assertTrue(relatorio.contains("POO"));
        assertTrue(relatorio.contains("Sem avaliações registradas."));
    }

    @Test
    @DisplayName("AC2 - Deve exibir detalhes da avaliação quando a turma possuir")
    void deveExibirDetalhesDaAvaliacao() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        Turma t = new Turma("CC3A", "POO");
        t.adicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        turmas.add(t);
        
        String relatorio = servico.gerarResumoAvaliacoes(turmas, "ADMIN");
        assertTrue(relatorio.contains("CC3A"));
        assertTrue(relatorio.contains("POO"));
        assertTrue(relatorio.contains("Prova"));
        assertTrue(relatorio.contains("10.0") || relatorio.contains("10,0"));
        assertTrue(relatorio.contains("0.5") || relatorio.contains("0,5"));
    }

    @Test
    @DisplayName("AC1, AC2, AC3 - Deve calcular peso total e indicar composição VÁLIDA (peso == 1.0)")
    void deveGerarRelatorioPesoValido() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        Turma t = new Turma("CC3A", "POO");
        t.adicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        t.adicionarAvaliacao(new Prova("P2", 10.0, 0.5));
        turmas.add(t);
        
        String relatorio = servico.gerarRelatorioPesos(turmas, "ADMIN");
        assertTrue(relatorio.contains("CC3A"));
        assertTrue(relatorio.contains("1.0") || relatorio.contains("1,0"));
        assertTrue(relatorio.contains("VÁLIDA"));
        assertFalse(relatorio.contains("INVÁLIDA"));
    }

    @Test
    @DisplayName("AC4 - Deve indicar composição INVÁLIDA (peso != 1.0)")
    void deveGerarRelatorioPesoInvalido() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        Turma t = new Turma("CC3A", "POO");
        t.adicionarAvaliacao(new Prova("P1", 10.0, 0.6));
        turmas.add(t);
        
        String relatorio = servico.gerarRelatorioPesos(turmas, "ADMIN");
        assertTrue(relatorio.contains("CC3A"));
        assertTrue(relatorio.contains("0.6") || relatorio.contains("0,6"));
        assertTrue(relatorio.contains("INVÁLIDA"));
    }

    @Test
    @DisplayName("AC5 - Deve exibir peso 0.0 para turma sem avaliações")
    void deveGerarRelatorioPesoZeroParaTurmaSemAvaliacao() {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new ArrayList<>();
        turmas.add(new Turma("CC3A", "POO"));
        
        String relatorio = servico.gerarRelatorioPesos(turmas, "ADMIN");
        assertTrue(relatorio.contains("CC3A"));
        assertTrue(relatorio.contains("0.0") || relatorio.contains("0,0"));
        assertTrue(relatorio.contains("INVÁLIDA"));
    }

    @Test
    @DisplayName("US-2377: AC1, AC4 - Deve gerar relatorio de persistencia se ADMIN")
    void deveGerarRelatorioPersistencia() {
        ServicoRelatorio servico = new ServicoRelatorio();
        String relatorio = servico.gerarRelatorioPersistencia("JSON", "ADMIN");
        assertTrue(relatorio.contains("JSON"));
        
        assertThrows(org.example.exception.ExcecaoAutorizacao.class, () -> {
            servico.gerarRelatorioPersistencia("JSON", "PROFESSOR");
        });
    }
}
