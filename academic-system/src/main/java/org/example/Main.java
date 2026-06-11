package org.example;

import org.example.model.Avaliacao;
import org.example.model.AvaliacaoFactory;
import org.example.model.GerenciadorDeTurmas;
import org.example.model.Turma;

public class Main {
    public static void main(String[] args) {
        GerenciadorDeTurmas gerenciador = new GerenciadorDeTurmas();
        gerenciador.salvarTurma(new Turma("CC3A", "Orientação a Objetos"));

        System.out.println("Bem-vindo ao Sistema Acadêmico!");
        System.out.println("-----------------------------------");

        System.out.println("Usuário logado: PROFESSOR");
        try {
            Avaliacao prova = AvaliacaoFactory.criar("Prova", "P1", 10.0, 0.4);
            gerenciador.registrarAvaliacao("CC3A", prova, "PROFESSOR");
            System.out.println("Sucesso: Você registrou a avaliação na turma CC3A!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("-----------------------------------");

        System.out.println("Usuário logado: Abner");
        try {
            Avaliacao trabalho = AvaliacaoFactory.criar("Trabalho Prático", "T1", 10.0, 0.2);
            gerenciador.registrarAvaliacao("CC3A", trabalho, "ALUNO_HACKER");
            System.out.println("Sucesso: Avaliação registrada!");
        } catch (Exception e) {
            System.out.println("Erro ao tentar registrar: " + e.getMessage());
        }
        
        System.out.println("-----------------------------------");
        System.out.println("--- US-2363: Cadastro de Turmas ---");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        org.example.service.TurmaService turmaService = new org.example.service.TurmaService();
        org.example.controller.TurmaController turmaController = new org.example.controller.TurmaController(turmaService);

        System.out.println("Simulando usuário logado como: ADMIN");
        String usuarioParaTurma = "ADMIN";
        
        System.out.print("Digite o código da nova turma: ");
        String codigo = scanner.nextLine();
        
        System.out.print("Digite o título da nova turma: ");
        String titulo = scanner.nextLine();

        try {
            turmaController.registrarTurma(codigo, titulo, usuarioParaTurma);
            System.out.println("Sucesso: Turma '" + titulo + "' registrada!");
            System.out.println("Turmas cadastradas atualmente na memória do Service:");
            for (Turma t : turmaController.listarTurmas()) {
                System.out.println("- " + t.getCodigo() + " : " + t.getTitulo());
            }
        } catch (org.example.exception.AcademicSystemException e) {
            System.out.println("Erro de Negócio ao registrar turma: " + e.getMessage());
        }
        
        scanner.close();
    }
}