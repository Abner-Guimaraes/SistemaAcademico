package org.example;

import org.example.service.TurmaService;
import org.example.controller.TurmaController;
import org.example.controller.AvaliacaoController;

public class AcademicSystem {
    private static AcademicSystem instance;
    
    private TurmaService turmaService;
    private TurmaController turmaController;
    private AvaliacaoController avaliacaoController;

    private AcademicSystem() {
        inicializarComponentes();
    }

    public static synchronized AcademicSystem getInstance() {
        if (instance == null) {
            instance = new AcademicSystem();
        }
        return instance;
    }
    
    private void inicializarComponentes() {
        this.turmaService = new TurmaService();
        this.turmaController = new TurmaController(this.turmaService);
        this.avaliacaoController = new AvaliacaoController(this.turmaService);
    }

    public TurmaController getTurmaController() {
        return turmaController;
    }
    
    public AvaliacaoController getAvaliacaoController() {
        return avaliacaoController;
    }
}
