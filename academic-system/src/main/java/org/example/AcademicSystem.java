package org.example;

import org.example.service.TurmaService;
import org.example.controller.TurmaController;
import org.example.controller.AvaliacaoController;
import org.example.controller.AcademicSystemController;

public class AcademicSystem {
    private static AcademicSystem instance;
    
    private TurmaService turmaService;
    private TurmaController turmaController;
    private AvaliacaoController avaliacaoController;
    private AcademicSystemController academicSystemController;

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
        this.academicSystemController = new AcademicSystemController(this.turmaController, this.avaliacaoController);
    }

    public AcademicSystemController getAcademicSystemController() {
        return academicSystemController;
    }
}
