package org.example.service;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Turma;
import org.example.exception.ExcecaoAutorizacao;
import org.example.validation.ValidadorDominio;

public class ServicoTurma {
    
    private final List<Turma> turmasCadastradas = new ArrayList<>();
    
    public void registrarTurma(String codigo, String titulo, String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem registrar turmas.");
        }
        Turma novaTurma = new Turma(codigo, titulo);
        ValidadorDominio.validate(novaTurma);
        turmasCadastradas.add(novaTurma);
    }
    
    public List<Turma> listarTurmas() {
        return turmasCadastradas;
    }
}