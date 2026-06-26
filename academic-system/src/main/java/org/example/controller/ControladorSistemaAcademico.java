package org.example.controller;

import org.example.model.Turma;
import org.example.service.ServicoRelatorio;
import org.example.service.ServicoPersistencia;
import java.util.List;

public class ControladorSistemaAcademico {
    private final ControladorTurma controladorTurma;
    private final ControladorAvaliacao controladorAvaliacao;
    private final ServicoRelatorio servicoRelatorio;
    private final ServicoPersistencia servicoPersistencia;

    public ControladorSistemaAcademico(ControladorTurma controladorTurma, ControladorAvaliacao controladorAvaliacao, ServicoRelatorio servicoRelatorio, ServicoPersistencia servicoPersistencia) {
        this.controladorTurma = controladorTurma;
        this.controladorAvaliacao = controladorAvaliacao;
        this.servicoRelatorio = servicoRelatorio;
        this.servicoPersistencia = servicoPersistencia;
    }

    public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
        controladorTurma.registrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> listarTurmas() {
        return controladorTurma.listarTurmas();
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        controladorAvaliacao.registrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
    }

    public void configurarPersistencia(String tipo, String usuarioLogado) {
        servicoPersistencia.configurarPersistencia(tipo, usuarioLogado);
    }

    public void salvarDados(String usuarioLogado) {
        servicoPersistencia.salvarDados(listarTurmas(), usuarioLogado);
    }

    public String gerarResumoAvaliacoes(String usuarioLogado) {
        return servicoRelatorio.gerarResumoAvaliacoes(listarTurmas(), usuarioLogado);
    }

    public String gerarRelatorioPesos(String usuarioLogado) {
        return servicoRelatorio.gerarRelatorioPesos(listarTurmas(), usuarioLogado);
    }

    public String gerarRelatorioPersistencia(String usuarioLogado) {
        return servicoRelatorio.gerarRelatorioPersistencia(servicoPersistencia.getTipoPersistencia(), usuarioLogado);
    }
}
