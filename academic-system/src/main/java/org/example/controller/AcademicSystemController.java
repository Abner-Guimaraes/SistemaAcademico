package org.example.controller;

import org.example.model.Turma;
import java.util.List;

public class AcademicSystemController {
    private final TurmaController turmaController;
    private final AvaliacaoController avaliacaoController;

    public AcademicSystemController(TurmaController turmaController, AvaliacaoController avaliacaoController) {
        this.turmaController = turmaController;
        this.avaliacaoController = avaliacaoController;
    }

    public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
        turmaController.registrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> listarTurmas() {
        return turmaController.listarTurmas();
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        avaliacaoController.registrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
    }
}
