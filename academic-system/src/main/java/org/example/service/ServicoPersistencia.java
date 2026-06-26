package org.example.service;

import java.util.List;
import org.example.model.Turma;
import org.example.repository.RepositorioTurma;
import org.example.repository.RepositorioTurmaTxt;
import org.example.repository.RepositorioTurmaXml;
import org.example.repository.RepositorioTurmaJson;
import org.example.exception.ExcecaoAutorizacao;
import org.example.exception.ExcecaoSistemaAcademico;

public class ServicoPersistencia {

    private String tipoPersistenciaAtual = "TXT";
    
    public void configurarPersistencia(String tipo, String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem configurar a persistência.");
        }
        
        if ("TXT".equalsIgnoreCase(tipo) || "XML".equalsIgnoreCase(tipo) || "JSON".equalsIgnoreCase(tipo)) {
            this.tipoPersistenciaAtual = tipo.toUpperCase();
        } else {
            throw new ExcecaoSistemaAcademico("Tipo de persistência não suportado: " + tipo);
        }
    }
    
    public String getTipoPersistencia() {
        return this.tipoPersistenciaAtual;
    }

    public void salvarDados(List<Turma> turmas, String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem salvar dados.");
        }
        
        RepositorioTurma repo;
        switch (tipoPersistenciaAtual) {
            case "TXT":
                repo = new RepositorioTurmaTxt();
                break;
            case "XML":
                repo = new RepositorioTurmaXml();
                break;
            case "JSON":
                repo = new RepositorioTurmaJson();
                break;
            default:
                throw new ExcecaoSistemaAcademico("Repositório não configurado para: " + tipoPersistenciaAtual);
        }
        
        repo.salvarTodas(turmas);
    }
}
