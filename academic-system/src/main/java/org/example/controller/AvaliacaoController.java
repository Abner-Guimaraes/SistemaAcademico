package org.example.controller;

import org.example.service.TurmaService;

public class AvaliacaoController {

    private final TurmaService turmaService;

    public AvaliacaoController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        turmaService.registrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
    }
}
