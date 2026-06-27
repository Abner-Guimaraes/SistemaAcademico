package org.example.service;

import org.example.model.Turma;
import org.example.model.Avaliacao;
import org.example.model.AvaliacaoFactory;
import org.example.exception.ExcecaoSistemaAcademico;
import org.example.exception.ExcecaoAutorizacao;
import org.example.validation.ValidadorDominio;

public class ServicoAvaliacao {

    private final ServicoTurma servicoTurma;

    public ServicoAvaliacao(ServicoTurma servicoTurma) {
        this.servicoTurma = servicoTurma;
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        if (!"PROFESSOR".equals(usuarioLogado)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas professores podem registrar avaliações.");
        }

        Turma turmaEncontrada = null;
        for (Turma t : servicoTurma.listarTurmas()) {
            if (t.getCodigo().equals(codigoTurma)) {
                turmaEncontrada = t;
                break;
            }
        }

        if (turmaEncontrada == null) {
            throw new ExcecaoSistemaAcademico("Turma não encontrada: " + codigoTurma);
        }

        Avaliacao novaAvaliacao = AvaliacaoFactory.criar(tipo, nome, valor, peso);
        ValidadorDominio.validate(novaAvaliacao);

        turmaEncontrada.adicionarAvaliacao(novaAvaliacao);
    }
}
