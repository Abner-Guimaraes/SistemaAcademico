using SistemaAcademico.Models;
using SistemaAcademico.Repositories;
using SistemaAcademico.Api;
using SistemaAcademico.Exceptions;
using Microsoft.Extensions.Logging;

namespace SistemaAcademico.Services;

// Equivalente à classe Java: ServicoPersistencia
// Padrão de projeto: Strategy (seleção do repositório em runtime) - preservado
// java.util.logging.Logger → Microsoft.Extensions.Logging.ILogger
public class ServicoPersistencia
{
    private static readonly ILogger<ServicoPersistencia> logger =
        LoggerFactory.Create(b => b.AddConsole()).CreateLogger<ServicoPersistencia>();

    private string tipoPersistenciaAtual = "TXT";

    public void ConfigurarPersistencia(string tipo, string usuarioAdmin)
    {
        if (!"ADMIN".Equals(usuarioAdmin))
        {
            logger.LogWarning("Falha de autorização: Tentativa de configurar persistência por {Usuario}", usuarioAdmin);
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem configurar a persistência.");
        }

        if (string.Equals("TXT", tipo, StringComparison.OrdinalIgnoreCase) ||
            string.Equals("XML", tipo, StringComparison.OrdinalIgnoreCase) ||
            string.Equals("JSON", tipo, StringComparison.OrdinalIgnoreCase))
        {
            this.tipoPersistenciaAtual = tipo.ToUpper();
            logger.LogInformation("Persistência configurada para {Tipo} pelo administrador.", this.tipoPersistenciaAtual);
        }
        else
        {
            throw new ExcecaoSistemaAcademico("Tipo de persistência não suportado: " + tipo);
        }
    }

    public string GetTipoPersistencia()
    {
        return this.tipoPersistenciaAtual;
    }

    public void SalvarDados(List<Turma> turmas, string usuarioAdmin)
    {
        if (!"ADMIN".Equals(usuarioAdmin))
        {
            logger.LogWarning("Falha de autorização: Tentativa de salvar dados por {Usuario}", usuarioAdmin);
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem salvar dados.");
        }

        // Padrão Strategy: seleção do repositório conforme tipo configurado
        IRepositorioTurma repo;
        switch (tipoPersistenciaAtual)
        {
            case "TXT":
                repo = new RepositorioTurmaTxt();
                break;
            case "XML":
                repo = new RepositorioTurmaXml();
                break;
            case "JSON":
                repo = new RepositorioTurmaJson();
                break;
            default:
                throw new ExcecaoSistemaAcademico("Repositório não configurado para: " + tipoPersistenciaAtual);
        }

        repo.SalvarTodas(turmas);
        logger.LogInformation("Dados acadêmicos salvos localmente com sucesso no formato {Tipo}", tipoPersistenciaAtual);

        // US-2418: Sincronizar remotamente
        try
        {
            IArmazenamentoRemoto apiRemota = new ArmazenamentoRemotoAwsS3();
            apiRemota.Autenticar("TOKEN_SECRETO_AWS_S3_12345");
            apiRemota.EnviarDados(turmas);
        }
        catch (Exception e)
        {
            logger.LogWarning("Falha na sincronização remota: {Mensagem}", e.Message);
        }
    }
}
