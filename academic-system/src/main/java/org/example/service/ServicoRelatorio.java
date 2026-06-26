package org.example.service;

import java.util.List;
import org.example.model.Turma;
import org.example.model.Avaliacao;

public class ServicoRelatorio {
    
    public String gerarResumoAvaliacoes(List<Turma> turmas, String usuarioLogado) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Relatório de Resumo de Avaliações ---\n");
        
        if (turmas == null || turmas.isEmpty()) {
            sb.append("Nenhuma turma registrada.\n");
            return sb.toString();
        }

        for (Turma t : turmas) {
            sb.append("Turma: ").append(t.getCodigo()).append(" - ").append(t.getTitulo()).append("\n");
            
            List<Avaliacao> avaliacoes = t.getAvaliacoes();
            if (avaliacoes.isEmpty()) {
                sb.append("  Sem avaliações registradas.\n");
            } else {
                for (Avaliacao a : avaliacoes) {
                    sb.append("  Avaliação: ")
                      .append(a.getClass().getSimpleName())
                      .append(" | Valor: ").append(a.getValor())
                      .append(" | Peso: ").append(a.getPeso())
                      .append("\n");
                }
            }
        }
        return sb.toString();
    }

    public String gerarRelatorioPesos(List<Turma> turmas, String usuarioLogado) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Relatório de Pesos das Avaliações ---\n");
        
        if (turmas == null || turmas.isEmpty()) {
            sb.append("Nenhuma turma registrada.\n");
            return sb.toString();
        }

        for (Turma t : turmas) {
            double pesoTotal = 0.0;
            for (Avaliacao a : t.getAvaliacoes()) {
                pesoTotal += a.getPeso();
            }
            
            sb.append("Turma: ").append(t.getCodigo()).append(" - ").append(t.getTitulo()).append("\n");
            sb.append("  Peso Total: ").append(String.format(java.util.Locale.US, "%.1f", pesoTotal)).append("\n");
            
            if (Math.abs(pesoTotal - 1.0) < 0.0001) {
                sb.append("  Status: Composição VÁLIDA\n");
            } else {
                sb.append("  Status: Composição INVÁLIDA\n");
            }
        }
        return sb.toString();
    }

    public String gerarRelatorioPersistencia(String tipoAtual, String usuarioLogado) {
        if (!"ADMIN".equals(usuarioLogado)) {
            throw new org.example.exception.ExcecaoAutorizacao("Operação negada: Apenas administradores podem gerar relatório de persistência.");
        }
        return "--- Relatório de Configuração de Persistência ---\nTipo de Persistência Atual: " + tipoAtual + "\n";
    }
}
