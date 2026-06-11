package org.example.model;

import org.example.exception.ExcecaoSistemaAcademico;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeTurmas {
    
    // Lista que simula o banco de dados do sistema
    private List<Turma> turmas = new ArrayList<>();

    // Método para salvar as turmas que existem na faculdade
    public void salvarTurma(Turma turma) {
        turmas.add(turma);
    }

    // Método principal que gerencia o registro das avaliações (Cobre AC4 e AC8)
    public void registrarAvaliacao(String codigoTurma, Avaliacao avaliacao, String usuarioLogado) {
        
        // AC8: Verifica a autorização imediatamente
        if (!"PROFESSOR".equals(usuarioLogado)) {
            throw new ExcecaoSistemaAcademico("Operação negada: Apenas professores podem registrar avaliações.");
        }

        // Se for professor, procura a turma pelo código (AC4)
        for (Turma turma : turmas) {
            if (turma.getCodigo().equals(codigoTurma)) {
                turma.adicionarAvaliacao(avaliacao); // Registra na turma
                return; // Operação concluída com sucesso
            }
        }
        
        // Se rodou a lista toda e não encontrou, lança o erro de turma inexistente
        throw new ExcecaoSistemaAcademico("Turma inexistente: " + codigoTurma);
    }
}