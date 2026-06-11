package org.example.controller;

import org.example.service.TurmaService;
import org.example.model.Turma;
import java.util.List;

public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
        // Delega a operação de negócio para a camada de Service (AC7)
        turmaService.registrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> listarTurmas() {
        return turmaService.listarTurmas();
    }
}
