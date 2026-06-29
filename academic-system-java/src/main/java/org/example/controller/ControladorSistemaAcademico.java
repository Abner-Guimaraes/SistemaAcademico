package org.example.controller;

import org.example.model.Turma;
import org.example.model.Usuario;
import org.example.service.ServicoTurma;
import org.example.service.ServicoAvaliacao;
import org.example.service.ServicoRelatorio;
import org.example.service.ServicoPersistencia;
import org.example.service.ServicoSeguranca;
import java.util.List;

public class ControladorSistemaAcademico {
    private final ServicoTurma servicoTurma;
    private final ServicoAvaliacao servicoAvaliacao;
    private final ServicoRelatorio servicoRelatorio;
    private final ServicoPersistencia servicoPersistencia;
    private final ServicoSeguranca servicoSeguranca;

    public ControladorSistemaAcademico(ServicoTurma servicoTurma, ServicoAvaliacao servicoAvaliacao, ServicoRelatorio servicoRelatorio, ServicoPersistencia servicoPersistencia, ServicoSeguranca servicoSeguranca) {
        this.servicoTurma = servicoTurma;
        this.servicoAvaliacao = servicoAvaliacao;
        this.servicoRelatorio = servicoRelatorio;
        this.servicoPersistencia = servicoPersistencia;
        this.servicoSeguranca = servicoSeguranca;
    }

    public Usuario autenticar(String username, String password) {
        return servicoSeguranca.autenticar(username, password);
    }

    public void logout(String username) {
        servicoSeguranca.logout(username);
    }

    public void registrarTurma(String codigo, String titulo, String usuarioLogado) {
        servicoTurma.registrarTurma(codigo, titulo, usuarioLogado);
    }

    public List<Turma> listarTurmas() {
        return servicoTurma.listarTurmas();
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        servicoAvaliacao.registrarAvaliacao(codigoTurma, nome, tipo, valor, peso, usuarioLogado);
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
