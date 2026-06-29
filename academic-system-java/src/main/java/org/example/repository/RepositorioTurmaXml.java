package org.example.repository;

import org.example.model.Turma;
import org.example.model.Avaliacao;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.example.exception.ExcecaoSistemaAcademico;

public class RepositorioTurmaXml implements RepositorioTurma {
    
    private String arquivo = "turmas.xml";

    public RepositorioTurmaXml() {}
    public RepositorioTurmaXml(String arquivo) { this.arquivo = arquivo; }

    @Override
    public void salvarTodas(List<Turma> turmas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<sistemaAcademico>");
            writer.println("  <turmas>");
            for (Turma turma : turmas) {
                writer.println("    <turma>");
                writer.println("      <codigo>" + turma.getCodigo() + "</codigo>");
                writer.println("      <titulo>" + turma.getTitulo() + "</titulo>");
                writer.println("      <avaliacoes>");
                for (Avaliacao avaliacao : turma.getAvaliacoes()) {
                    writer.println("        <avaliacao>");
                    writer.println("          <tipo>" + avaliacao.getClass().getSimpleName() + "</tipo>");
                    writer.println("          <valor>" + avaliacao.getValor() + "</valor>");
                    writer.println("          <peso>" + avaliacao.getPeso() + "</peso>");
                    writer.println("        </avaliacao>");
                }
                writer.println("      </avaliacoes>");
                writer.println("    </turma>");
            }
            writer.println("  </turmas>");
            writer.println("</sistemaAcademico>");
        } catch (IOException e) {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo XML: " + e.getMessage());
        }
    }
}
