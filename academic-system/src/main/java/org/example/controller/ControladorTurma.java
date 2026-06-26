package org.example.controller;

import org.example.service.ServicoTurma;
import org.example.model.Turma;
import java.util.List;

public class ControladorTurma {

    private final ServicoTurma servicoTurma;

    public ControladorTurma(ServicoTurma servicoTurma) {
        this.servicoTurma = servicoTurma;
    }

    public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
        // Delega a operação de negócio para a camada de Service (AC7)
        servicoTurma.registrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> listarTurmas() {
        return servicoTurma.listarTurmas();
    }

    public void salvarDadosTxt(String usuarioLogado) {
        servicoTurma.salvarDadosTxt(usuarioLogado);
    }
}
