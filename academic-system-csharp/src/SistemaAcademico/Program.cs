using SistemaAcademico;
using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using SistemaAcademico.Controllers;

// Equivalente à classe Java: Main
// Ponto de entrada do sistema — interface Console
// Comportamento idêntico ao Main.java original

ControladorSistemaAcademico controller = SistemaAcademico.SistemaAcademico.GetInstance().GetAcademicSystemController();

bool executando = true;

while (executando)
{
    Console.WriteLine("\n=== TELA DE LOGIN ===");
    Console.Write("Usuário (ou 'sair' para encerrar): ");
    string? username = Console.ReadLine() ?? "";

    if (username.Equals("sair", StringComparison.OrdinalIgnoreCase))
    {
        executando = false;
        break;
    }

    Console.Write("Senha: ");
    string? password = Console.ReadLine() ?? "";

    Usuario usuarioLogadoObj;
    try
    {
        usuarioLogadoObj = controller.Autenticar(username, password);
    }
    catch (ExcecaoAutenticacao e)
    {
        Console.WriteLine("Erro de Segurança: " + e.Message);
        continue;
    }

    string usuarioLogado = usuarioLogadoObj.Role;
    Console.WriteLine("Login efetuado com sucesso como " + usuarioLogado);

    bool logado = true;
    while (logado)
    {
        Console.WriteLine("\n=== MENU ACADÊMICO ===");
        Console.WriteLine("Usuário logado: " + usuarioLogado);

        if (usuarioLogado.Equals("ADMIN"))
        {
            Console.WriteLine("1. Cadastrar Turma");
            Console.WriteLine("2. Configurar Persistência");
            Console.WriteLine("3. Salvar Dados");
            Console.WriteLine("4. Relatório de Persistência");
            Console.WriteLine("5. Listar Turmas");
            Console.WriteLine("6. Resumo de Avaliações (Relatório)");
            Console.WriteLine("7. Pesos das Avaliações (Relatório)");
            Console.WriteLine("8. Logout");
            Console.WriteLine("9. Sair do Sistema");
        }
        else if (usuarioLogado.Equals("PROFESSOR"))
        {
            Console.WriteLine("1. Cadastrar Avaliação");
            Console.WriteLine("2. Listar Turmas");
            Console.WriteLine("3. Resumo de Avaliações (Relatório)");
            Console.WriteLine("4. Pesos das Avaliações (Relatório)");
            Console.WriteLine("5. Logout");
            Console.WriteLine("6. Sair do Sistema");
        }

        Console.Write("Escolha uma opção: ");
        string opcao = Console.ReadLine() ?? "";

        try
        {
            if (usuarioLogado.Equals("ADMIN"))
            {
                switch (opcao)
                {
                    case "1":
                        Console.Write("Digite o código da nova turma: ");
                        string codigo = Console.ReadLine() ?? "";
                        Console.Write("Digite o título da nova turma: ");
                        string titulo = Console.ReadLine() ?? "";
                        controller.RegistrarTurma(codigo, titulo, usuarioLogado);
                        Console.WriteLine("Turma '" + titulo + "' registrada com sucesso.");
                        break;
                    case "2":
                        Console.Write("Digite o tipo de persistência desejada (TXT, XML, JSON): ");
                        string tipo = Console.ReadLine() ?? "";
                        controller.ConfigurarPersistencia(tipo, usuarioLogado);
                        Console.WriteLine("Persistência configurada para: " + tipo.ToUpper());
                        break;
                    case "3":
                        controller.SalvarDados(usuarioLogado);
                        Console.WriteLine("Dados salvos com sucesso.");
                        break;
                    case "4":
                        Console.WriteLine(controller.GerarRelatorioPersistencia(usuarioLogado));
                        break;
                    case "5":
                        Console.WriteLine("Turmas cadastradas:");
                        foreach (Turma t in controller.ListarTurmas())
                        {
                            Console.WriteLine("- " + t.Codigo + " : " + t.Titulo);
                        }
                        break;
                    case "6":
                        Console.WriteLine(controller.GerarResumoAvaliacoes(usuarioLogado));
                        break;
                    case "7":
                        Console.WriteLine(controller.GerarRelatorioPesos(usuarioLogado));
                        break;
                    case "8":
                        controller.Logout(usuarioLogado);
                        logado = false;
                        Console.WriteLine("Logout efetuado com sucesso.");
                        break;
                    case "9":
                        logado = false;
                        executando = false;
                        break;
                    default:
                        throw new ExcecaoEntradaTeclado("Opção de menu inválida.");
                }
            }
            else if (usuarioLogado.Equals("PROFESSOR"))
            {
                switch (opcao)
                {
                    case "1":
                        Console.Write("Digite o código da turma: ");
                        string codigoTurma = Console.ReadLine() ?? "";
                        Console.Write("Digite o nome da avaliação: ");
                        string nomeAvaliacao = Console.ReadLine() ?? "";
                        Console.Write("Digite o tipo da avaliação (Prova, Trabalho Prático, Seminário, Atividade): ");
                        string tipoAvaliacao = Console.ReadLine() ?? "";

                        double valorAvaliacao;
                        Console.Write("Digite o valor da avaliação: ");
                        try
                        {
                            valorAvaliacao = double.Parse(Console.ReadLine() ?? "", System.Globalization.CultureInfo.InvariantCulture);
                        }
                        catch (FormatException)
                        {
                            throw new ExcecaoEntradaTeclado("Valor numérico inválido informado para avaliação.");
                        }

                        double pesoAvaliacao;
                        Console.Write("Digite o peso da avaliação: ");
                        try
                        {
                            pesoAvaliacao = double.Parse(Console.ReadLine() ?? "", System.Globalization.CultureInfo.InvariantCulture);
                        }
                        catch (FormatException)
                        {
                            throw new ExcecaoEntradaTeclado("Valor numérico inválido informado para peso.");
                        }

                        controller.RegistrarAvaliacao(codigoTurma, nomeAvaliacao, tipoAvaliacao, valorAvaliacao, pesoAvaliacao, usuarioLogado);
                        Console.WriteLine("Avaliação registrada com sucesso.");
                        break;
                    case "2":
                        Console.WriteLine("Turmas cadastradas:");
                        foreach (Turma t in controller.ListarTurmas())
                        {
                            Console.WriteLine("- " + t.Codigo + " : " + t.Titulo);
                        }
                        break;
                    case "3":
                        Console.WriteLine(controller.GerarResumoAvaliacoes(usuarioLogado));
                        break;
                    case "4":
                        Console.WriteLine(controller.GerarRelatorioPesos(usuarioLogado));
                        break;
                    case "5":
                        controller.Logout(usuarioLogado);
                        logado = false;
                        Console.WriteLine("Logout efetuado com sucesso.");
                        break;
                    case "6":
                        logado = false;
                        executando = false;
                        break;
                    default:
                        throw new ExcecaoEntradaTeclado("Opção de menu inválida.");
                }
            }
        }
        catch (ExcecaoSistemaAcademico e)
        {
            Console.WriteLine("Erro de Domínio: " + e.Message);
        }
        catch (ExcecaoSegurancaSistema e)
        {
            Console.WriteLine("Erro de Segurança: " + e.Message);
        }
        catch (ExcecaoEntradaTeclado e)
        {
            Console.WriteLine("Erro de Entrada de Usuário: " + e.Message);
        }
        catch (Exception e)
        {
            Console.WriteLine("Erro inesperado: " + e.Message);
        }
    }
}

Console.WriteLine("Sistema encerrado.");
