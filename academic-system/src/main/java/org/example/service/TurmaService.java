package org.example.service;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Turma;
import org.example.model.Avaliacao;
import org.example.model.AvaliacaoFactory;
import org.example.exception.AcademicSystemException; // Importando a exceção que criamos
import org.example.exception.AuthorizationException;

public class TurmaService {
    
    private final List<Turma> turmasCadastradas = new ArrayList<>();
    
    public void registrarTurma(String codigo, String titulo, String usuarioAdmin) {
        
        // 1. CLÁUSULA DE GUARDA: Barramos o erro logo no início!
        // Em Java, SEMPRE compare Strings usando .equals(), nunca usando ==
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new AuthorizationException("Operação negada: Apenas administradores podem registrar turmas.");
        }
        
        // 2. VALIDAÇÃO DE DOMÍNIO: AC3 e AC4
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new AcademicSystemException("Dados inválidos: O código da turma não pode ser vazio.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new AcademicSystemException("Dados inválidos: O título da turma não pode ser vazio.");
        }
        
        Turma novaTurma = new Turma(codigo, titulo);
        turmasCadastradas.add(novaTurma);
    }
    
    public List<Turma> listarTurmas() {
        return turmasCadastradas;
    }

    public void registrarAvaliacao(String codigoTurma, String nome, String tipo, double valor, double peso, String usuarioLogado) {
        // 1. Verificação de autorização (AC8)
        if (!"PROFESSOR".equals(usuarioLogado)) {
            throw new AuthorizationException("Operação negada: Apenas professores podem registrar avaliações.");
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
            throw new AcademicSystemException("Turma não encontrada: " + codigoTurma);
        }

        // Chama a Factory para instanciar (o tipo é validado dentro da factory)
        Avaliacao novaAvaliacao = AvaliacaoFactory.criar(tipo, nome, valor, peso);

        // Adiciona à turma
        turmaEncontrada.adicionarAvaliacao(novaAvaliacao);
    }
}