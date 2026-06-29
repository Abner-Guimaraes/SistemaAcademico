package org.example.repository;

import org.example.model.Turma;
import org.example.model.Avaliacao;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.example.exception.ExcecaoSistemaAcademico;

public class RepositorioTurmaTxt implements RepositorioTurma {
    
    private String arquivo = "turmas.txt";

    public RepositorioTurmaTxt() {
    }

    public RepositorioTurmaTxt(String arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvarTodas(List<Turma> turmas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            for (Turma turma : turmas) {
                // AC4: The TXT file must contain at least: class code, class title, assessment type, assessment value, assessment weight.
                List<Avaliacao> avaliacoes = turma.getAvaliacoes();
                if (avaliacoes.isEmpty()) {
                    writer.printf("%s|%s|SemAvaliacao|0.0|0.0%n",
                        turma.getCodigo(),
                        turma.getTitulo()
                    );
                } else {
                    for (Avaliacao avaliacao : avaliacoes) {
                        writer.printf("%s|%s|%s|%.2f|%.2f%n",
                            turma.getCodigo(),
                            turma.getTitulo(),
                            avaliacao.getClass().getSimpleName(),
                            avaliacao.getValor(),
                            avaliacao.getPeso()
                        );
                    }
                }
            }
        } catch (IOException e) {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo TXT: " + e.getMessage());
        }
    }
}
