package org.example.controller;

import org.example.service.ServicoTurma;

public class ControladorAvaliacao {

    private final ServicoTurma servicoTurma;

    public ControladorAvaliacao(ServicoTurma servicoTurma) {
        this.servicoTurma = servicoTurma;
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        servicoTurma.registrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
    }
}
