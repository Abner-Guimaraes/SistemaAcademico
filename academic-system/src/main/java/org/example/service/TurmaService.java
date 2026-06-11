package org.example.service;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Turma;
import org.example.exception.AcademicSystemException; // Importando a exceção que criamos

public class TurmaService {
    
    private final List<Turma> turmasCadastradas = new ArrayList<>();
    
    public void registrarTurma(String codigo, String titulo, String usuarioAdmin) {
        
        // 1. CLÁUSULA DE GUARDA: Barramos o erro logo no início!
        // Em Java, SEMPRE compare Strings usando .equals(), nunca usando ==
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new AcademicSystemException("Operação negada: Apenas administradores podem registrar turmas.");
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
}