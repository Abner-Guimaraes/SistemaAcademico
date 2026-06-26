package org.example;

import org.example.service.ServicoTurma;
import org.example.service.ServicoRelatorio;
import org.example.service.ServicoPersistencia;
import org.example.controller.ControladorTurma;
import org.example.controller.ControladorAvaliacao;
import org.example.controller.ControladorSistemaAcademico;

public class SistemaAcademico {
    private static SistemaAcademico instance;
    
    private ServicoTurma servicoTurma;
    private ServicoRelatorio servicoRelatorio;
    private ServicoPersistencia servicoPersistencia;
    private ControladorTurma controladorTurma;
    private ControladorAvaliacao controladorAvaliacao;
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
        this.servicoRelatorio = new ServicoRelatorio();
        this.servicoPersistencia = new ServicoPersistencia();
        this.controladorTurma = new ControladorTurma(this.servicoTurma);
        this.controladorAvaliacao = new ControladorAvaliacao(this.servicoTurma);
        this.controladorSistemaAcademico = new ControladorSistemaAcademico(this.controladorTurma, this.controladorAvaliacao, this.servicoRelatorio, this.servicoPersistencia);
    }

    public ControladorSistemaAcademico getAcademicSystemController() {
        return controladorSistemaAcademico;
    }
}
