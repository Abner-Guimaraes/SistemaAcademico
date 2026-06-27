package org.example.service;

import java.util.List;
import org.example.model.Turma;
import org.example.repository.RepositorioTurma;
import org.example.repository.RepositorioTurmaTxt;
import org.example.repository.RepositorioTurmaXml;
import org.example.repository.RepositorioTurmaJson;
import org.example.exception.ExcecaoAutorizacao;
import org.example.exception.ExcecaoSistemaAcademico;
import java.util.logging.Logger;

public class ServicoPersistencia {

    private static final Logger logger = Logger.getLogger(ServicoPersistencia.class.getName());
    private String tipoPersistenciaAtual = "TXT";
    
    public void configurarPersistencia(String tipo, String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            logger.warning("Falha de autorização: Tentativa de configurar persistência por " + usuarioAdmin);
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem configurar a persistência.");
        }
        
        if ("TXT".equalsIgnoreCase(tipo) || "XML".equalsIgnoreCase(tipo) || "JSON".equalsIgnoreCase(tipo)) {
            this.tipoPersistenciaAtual = tipo.toUpperCase();
            logger.info("Persistência configurada para " + this.tipoPersistenciaAtual + " pelo administrador.");
        } else {
            throw new ExcecaoSistemaAcademico("Tipo de persistência não suportado: " + tipo);
        }
    }
    
    public String getTipoPersistencia() {
        return this.tipoPersistenciaAtual;
    }

    public void salvarDados(List<Turma> turmas, String usuarioAdmin) {
        if (!"ADMIN".equals(usuarioAdmin)) {
            logger.warning("Falha de autorização: Tentativa de salvar dados por " + usuarioAdmin);
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
        logger.info("Dados acadêmicos salvos localmente com sucesso no formato " + tipoPersistenciaAtual);

        // US-2418: Sincronizar remotamente
        try {
            org.example.api.ArmazenamentoRemoto apiRemota = new org.example.api.ArmazenamentoRemotoAwsS3();
            apiRemota.autenticar("TOKEN_SECRETO_AWS_S3_12345");
            apiRemota.enviarDados(turmas);
        } catch (Exception e) {
            logger.warning("Falha na sincronização remota: " + e.getMessage());
        }
    }
}
