package org.example.api;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.example.model.Turma;
import org.example.exception.ExcecaoSistemaAcademico;

public class ArmazenamentoRemotoAwsS3 implements ArmazenamentoRemoto {

    private static final Logger logger = Logger.getLogger(ArmazenamentoRemotoAwsS3.class.getName());
    private boolean autenticado = false;

    @Override
    public boolean autenticar(String token) {
        // Simulação de autenticação com AWS S3
        if (token != null && !token.isBlank()) {
            this.autenticado = true;
            logger.info("[API AWS S3] Autenticação realizada com sucesso usando token seguro.");
            return true;
        }
        logger.warning("[API AWS S3] Falha na autenticação remota: Token inválido.");
        return false;
    }

    @Override
    public void enviarDados(List<Turma> turmas) {
        if (!autenticado) {
            throw new ExcecaoSistemaAcademico("Acesso negado à API Remota: Não autenticado.");
        }
        logger.info("[API AWS S3] Iniciando upload de " + turmas.size() + " turmas para o bucket na nuvem...");
        logger.info("[API AWS S3] Dados sincronizados e armazenados remotamente com sucesso.");
    }

    @Override
    public List<Turma> recuperarDados() {
        if (!autenticado) {
            throw new ExcecaoSistemaAcademico("Acesso negado à API Remota: Não autenticado.");
        }
        logger.info("[API AWS S3] Baixando dados do bucket na nuvem...");
        return new ArrayList<>(); // Simulação de dados vazios vindos da nuvem
    }
}
