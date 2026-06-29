package org.example;

import org.example.model.Turma;
import org.example.model.Usuario;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaAcademico system = SistemaAcademico.getInstance();
        org.example.controller.ControladorSistemaAcademico controller = system.getAcademicSystemController();

        boolean executando = true;

        while (executando) {
            System.out.println("\n=== TELA DE LOGIN ===");
            System.out.print("Usuário (ou 'sair' para encerrar): ");
            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("sair")) {
                executando = false;
                break;
            }

            System.out.print("Senha: ");
            String password = scanner.nextLine();

            Usuario usuarioLogadoObj;
            try {
                usuarioLogadoObj = controller.autenticar(username, password);
            } catch (org.example.exception.ExcecaoAutenticacao e) {
                System.out.println("Erro de Segurança: " + e.getMessage());
                continue;
            }

            String usuarioLogado = usuarioLogadoObj.getRole();
            System.out.println("Login efetuado com sucesso como " + usuarioLogado);

            boolean logado = true;
            while (logado) {
                System.out.println("\n=== MENU ACADÊMICO ===");
                System.out.println("Usuário logado: " + usuarioLogado);
                
                if (usuarioLogado.equals("ADMIN")) {
                    System.out.println("1. Cadastrar Turma");
                    System.out.println("2. Configurar Persistência");
                    System.out.println("3. Salvar Dados");
                    System.out.println("4. Relatório de Persistência");
                    System.out.println("5. Listar Turmas");
                    System.out.println("6. Resumo de Avaliações (Relatório)");
                    System.out.println("7. Pesos das Avaliações (Relatório)");
                    System.out.println("8. Logout");
                    System.out.println("9. Sair do Sistema");
                } else if (usuarioLogado.equals("PROFESSOR")) {
                    System.out.println("1. Cadastrar Avaliação");
                    System.out.println("2. Listar Turmas");
                    System.out.println("3. Resumo de Avaliações (Relatório)");
                    System.out.println("4. Pesos das Avaliações (Relatório)");
                    System.out.println("5. Logout");
                    System.out.println("6. Sair do Sistema");
                }
                
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();

                try {
                    if (usuarioLogado.equals("ADMIN")) {
                        switch (opcao) {
                            case "1":
                                System.out.print("Digite o código da nova turma: ");
                                String codigo = scanner.nextLine();
                                System.out.print("Digite o título da nova turma: ");
                                String titulo = scanner.nextLine();
                                controller.registrarTurma(codigo, titulo, usuarioLogado);
                                System.out.println("Turma '" + titulo + "' registrada com sucesso.");
                                break;
                            case "2":
                                System.out.print("Digite o tipo de persistência desejada (TXT, XML, JSON): ");
                                String tipo = scanner.nextLine();
                                controller.configurarPersistencia(tipo, usuarioLogado);
                                System.out.println("Persistência configurada para: " + tipo.toUpperCase());
                                break;
                            case "3":
                                controller.salvarDados(usuarioLogado);
                                System.out.println("Dados salvos com sucesso.");
                                break;
                            case "4":
                                System.out.println(controller.gerarRelatorioPersistencia(usuarioLogado));
                                break;
                            case "5":
                                System.out.println("Turmas cadastradas:");
                                for (Turma t : controller.listarTurmas()) {
                                    System.out.println("- " + t.getCodigo() + " : " + t.getTitulo());
                                }
                                break;
                            case "6":
                                System.out.println(controller.gerarResumoAvaliacoes(usuarioLogado));
                                break;
                            case "7":
                                System.out.println(controller.gerarRelatorioPesos(usuarioLogado));
                                break;
                            case "8":
                                controller.logout(usuarioLogado);
                                logado = false;
                                System.out.println("Logout efetuado com sucesso.");
                                break;
                            case "9":
                                logado = false;
                                executando = false;
                                break;
                            default:
                                throw new org.example.exception.ExcecaoEntradaTeclado("Opção de menu inválida.");
                        }
                    } else if (usuarioLogado.equals("PROFESSOR")) {
                        switch (opcao) {
                            case "1":
                                System.out.print("Digite o código da turma: ");
                                String codigoTurma = scanner.nextLine();
                                System.out.print("Digite o nome da avaliação: ");
                                String nomeAvaliacao = scanner.nextLine();
                                System.out.print("Digite o tipo da avaliação (Prova, Trabalho Prático, Seminário, Atividade): ");
                                String tipoAvaliacao = scanner.nextLine();
                                
                                double valorAvaliacao;
                                System.out.print("Digite o valor da avaliação: ");
                                try {
                                    valorAvaliacao = Double.parseDouble(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    throw new org.example.exception.ExcecaoEntradaTeclado("Valor numérico inválido informado para avaliação.");
                                }
                                
                                double pesoAvaliacao;
                                System.out.print("Digite o peso da avaliação: ");
                                try {
                                    pesoAvaliacao = Double.parseDouble(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    throw new org.example.exception.ExcecaoEntradaTeclado("Valor numérico inválido informado para peso.");
                                }
                                
                                controller.registrarAvaliacao(codigoTurma, nomeAvaliacao, tipoAvaliacao, valorAvaliacao, pesoAvaliacao, usuarioLogado);
                                System.out.println("Avaliação registrada com sucesso.");
                                break;
                            case "2":
                                System.out.println("Turmas cadastradas:");
                                for (Turma t : controller.listarTurmas()) {
                                    System.out.println("- " + t.getCodigo() + " : " + t.getTitulo());
                                }
                                break;
                            case "3":
                                System.out.println(controller.gerarResumoAvaliacoes(usuarioLogado));
                                break;
                            case "4":
                                System.out.println(controller.gerarRelatorioPesos(usuarioLogado));
                                break;
                            case "5":
                                controller.logout(usuarioLogado);
                                logado = false;
                                System.out.println("Logout efetuado com sucesso.");
                                break;
                            case "6":
                                logado = false;
                                executando = false;
                                break;
                            default:
                                throw new org.example.exception.ExcecaoEntradaTeclado("Opção de menu inválida.");
                        }
                    }
                } catch (org.example.exception.ExcecaoSistemaAcademico e) {
                    System.out.println("Erro de Domínio: " + e.getMessage());
                } catch (org.example.exception.ExcecaoSegurancaSistema e) {
                    System.out.println("Erro de Segurança: " + e.getMessage());
                } catch (org.example.exception.ExcecaoEntradaTeclado e) {
                    System.out.println("Erro de Entrada de Usuário: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erro inesperado: " + e.getMessage());
                }
            }
        }
        
        System.out.println("Sistema encerrado.");
        scanner.close();
    }
}