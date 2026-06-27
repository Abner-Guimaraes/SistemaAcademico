package org.example;

import org.example.service.ServicoTurma;
import org.example.service.ServicoAvaliacao;
import org.example.service.ServicoRelatorio;
import org.example.service.ServicoPersistencia;
import org.example.service.ServicoSeguranca;
import org.example.repository.RepositorioUsuarioTxt;
import org.example.controller.ControladorSistemaAcademico;

public class SistemaAcademico {
    private static SistemaAcademico instance;
    
    private ServicoTurma servicoTurma;
    private ServicoAvaliacao servicoAvaliacao;
    private ServicoRelatorio servicoRelatorio;
    private ServicoPersistencia servicoPersistencia;
    private ServicoSeguranca servicoSeguranca;
    private ControladorSistemaAcademico controladorSistemaAcademico;

    private SistemaAcademico() {
        inicializarComponentes();
    }

    public static synchronized SistemaAcademico getInstance() {
        if (instance == null) {
            instance = new SistemaAcademico();
        }
        return instance;
    }
    
    private void inicializarComponentes() {
        this.servicoTurma = new ServicoTurma();
        this.servicoAvaliacao = new ServicoAvaliacao(this.servicoTurma);
        this.servicoRelatorio = new ServicoRelatorio();
        this.servicoPersistencia = new ServicoPersistencia();
        this.servicoSeguranca = new ServicoSeguranca(new RepositorioUsuarioTxt("users.txt"));
        this.controladorSistemaAcademico = new ControladorSistemaAcademico(this.servicoTurma, this.servicoAvaliacao, this.servicoRelatorio, this.servicoPersistencia, this.servicoSeguranca);
    }

    public ControladorSistemaAcademico getAcademicSystemController() {
        return controladorSistemaAcademico;
    }
}

