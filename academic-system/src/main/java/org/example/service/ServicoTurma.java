package org.example.service;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Turma;
import org.example.model.Avaliacao;
import org.example.model.AvaliacaoFactory;
import org.example.exception.ExcecaoSistemaAcademico; // Importando a exceção que criamos
import org.example.exception.ExcecaoAutorizacao;
import org.example.validation.ValidadorDominio;
import org.example.repository.RepositorioTurma;
import org.example.repository.RepositorioTurmaTxt;
public class ServicoTurma {
    
    private final List<Turma> turmasCadastradas = new ArrayList<>();
    
    public void registrarTurma(String codigo, String titulo, String usuarioAdmin) {
        
        // 1. CLÁUSULA DE GUARDA: Barramos o erro logo no início!
        // Em Java, SEMPRE compare Strings usando .equals(), nunca usando ==
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem registrar turmas.");
        }
        
        // 2. VALIDAÇÃO DE DOMÍNIO via Jakarta Validation
        Turma novaTurma = new Turma(codigo, titulo);
        ValidadorDominio.validate(novaTurma);
        turmasCadastradas.add(novaTurma);
    }
    
    public List<Turma> listarTurmas() {
        return turmasCadastradas;
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        // 1. Verificação de autorização (AC8)
        if (!"PROFESSOR".equals(usuarioLogado)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas professores podem registrar avaliações.");
        }

        // 2. Busca e validação da turma (AC4)
        Turma turmaEncontrada = null;
        for (Turma t : turmasCadastradas) {
            if (t.getCodigo().equals(codigoTurma)) {
                turmaEncontrada = t;
                break;
            }
        }

        if (turmaEncontrada == null) {
            throw new ExcecaoSistemaAcademico("Turma não encontrada: " + codigoTurma);
        }

        // Chama a Factory para instanciar e depois valida
        Avaliacao novaAvaliacao = AvaliacaoFactory.criar(tipo, nome, valor, peso);
        ValidadorDominio.validate(novaAvaliacao);

        // Adiciona à turma
        turmaEncontrada.adicionarAvaliacao(novaAvaliacao);
    }

    public void salvarDadosTxt(String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem salvar dados.");
        }
        RepositorioTurma repo = new RepositorioTurmaTxt();
        repo.salvarTodas(turmasCadastradas);
    }
}