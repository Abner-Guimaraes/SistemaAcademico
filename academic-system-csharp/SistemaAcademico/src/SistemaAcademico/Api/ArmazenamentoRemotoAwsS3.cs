using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using Microsoft.Extensions.Logging;

namespace SistemaAcademico.Api;

// Equivalente à classe Java: ArmazenamentoRemotoAwsS3 implements ArmazenamentoRemoto
// Simulação (mock) da API AWS S3 — comportamento idêntico ao original Java
public class ArmazenamentoRemotoAwsS3 : IArmazenamentoRemoto
{
    private static readonly ILogger<ArmazenamentoRemotoAwsS3> logger =
        LoggerFactory.Create(b => b.AddConsole()).CreateLogger<ArmazenamentoRemotoAwsS3>();

    private bool autenticado = false;

    public bool Autenticar(string token)
    {
        // Simulação de autenticação com AWS S3
        if (token != null && !string.IsNullOrWhiteSpace(token))
        {
            this.autenticado = true;
            logger.LogInformation("[API AWS S3] Autenticação realizada com sucesso usando token seguro.");
            return true;
        }
        logger.LogWarning("[API AWS S3] Falha na autenticação remota: Token inválido.");
        return false;
    }

    public void EnviarDados(List<Turma> turmas)
    {
        if (!autenticado)
        {
            throw new ExcecaoSistemaAcademico("Acesso negado à API Remota: Não autenticado.");
        }
        logger.LogInformation("[API AWS S3] Iniciando upload de {Count} turmas para o bucket na nuvem...", turmas.Count);
        logger.LogInformation("[API AWS S3] Dados sincronizados e armazenados remotamente com sucesso.");
    }

    public List<Turma> RecuperarDados()
    {
        if (!autenticado)
        {
            throw new ExcecaoSistemaAcademico("Acesso negado à API Remota: Não autenticado.");
        }
        logger.LogInformation("[API AWS S3] Baixando dados do bucket na nuvem...");
        return new List<Turma>(); // Simulação de dados vazios vindos da nuvem
    }
}
