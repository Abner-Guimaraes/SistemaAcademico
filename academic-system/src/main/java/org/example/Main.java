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

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.println("Usuário logado: PROFESSOR");
        System.out.println("--- US-2361: Registro de Avaliação ---");
        
        System.out.print("Digite o código da turma: ");
        String codigoTurma = scanner.nextLine();
        
        System.out.print("Digite o nome da avaliação (ex: P1): ");
        String nomeAvaliacao = scanner.nextLine();
        
        System.out.print("Digite o tipo da avaliação (Prova, Trabalho Prático, Seminário, Atividade): ");
        String tipoAvaliacao = scanner.nextLine();
        
        System.out.print("Digite o valor da avaliação: ");
        double valorAvaliacao = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Digite o peso da avaliação (ex: 0.4): ");
        double pesoAvaliacao = Double.parseDouble(scanner.nextLine());

        // FIM DA FASE 1 - Captura concluída. 
        // Os próximos passos (Fase 2 e 3) envolverão o Controller e o Service para processar essas variáveis.
        System.out.println("\n[DEBUG] Dados capturados: Turma=" + codigoTurma + ", Nome=" + nomeAvaliacao + ", Tipo=" + tipoAvaliacao + ", Valor=" + valorAvaliacao + ", Peso=" + pesoAvaliacao);
        
        System.out.println("-----------------------------------");
        System.out.println("--- US-2363: Cadastro de Turmas ---");
        // Reaproveitando o scanner criado acima
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