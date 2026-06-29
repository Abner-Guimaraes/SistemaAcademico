package org.example.repository;

import org.example.model.Turma;
import org.example.model.Avaliacao;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.example.exception.ExcecaoSistemaAcademico;

public class RepositorioTurmaJson implements RepositorioTurma {
    
    private String arquivo = "turmas.json";

    public RepositorioTurmaJson() {}
    public RepositorioTurmaJson(String arquivo) { this.arquivo = arquivo; }

    @Override
    public void salvarTodas(List<Turma> turmas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            writer.println("{");
            writer.println("  \"turmas\": [");
            for (int i = 0; i < turmas.size(); i++) {
                Turma turma = turmas.get(i);
                writer.println("    {");
                writer.println("      \"codigo\": \"" + turma.getCodigo() + "\",");
                writer.println("      \"titulo\": \"" + turma.getTitulo() + "\",");
                writer.println("      \"avaliacoes\": [");
                List<Avaliacao> avaliacoes = turma.getAvaliacoes();
                for (int j = 0; j < avaliacoes.size(); j++) {
                    Avaliacao avaliacao = avaliacoes.get(j);
                    writer.println("        {");
                    writer.println("          \"tipo\": \"" + avaliacao.getClass().getSimpleName() + "\",");
                    writer.println("          \"valor\": " + avaliacao.getValor() + ",");
                    writer.println("          \"peso\": " + avaliacao.getPeso());
                    writer.print("        }");
                    if (j < avaliacoes.size() - 1) writer.println(",");
                    else writer.println();
                }
                writer.println("      ]");
                writer.print("    }");
                if (i < turmas.size() - 1) writer.println(",");
                else writer.println();
            }
            writer.println("  ]");
            writer.println("}");
        } catch (IOException e) {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo JSON: " + e.getMessage());
        }
    }
}
