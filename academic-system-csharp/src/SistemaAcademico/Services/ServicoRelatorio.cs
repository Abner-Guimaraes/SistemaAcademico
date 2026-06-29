using System.Globalization;
using System.Text;
using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using Microsoft.Extensions.Logging;

namespace SistemaAcademico.Services;

// Equivalente à classe Java: ServicoRelatorio
// java.util.logging.Logger → Microsoft.Extensions.Logging.ILogger
// String.format(Locale.US, "%.1f", pesoTotal) → pesoTotal.ToString("F1", CultureInfo.InvariantCulture)
public class ServicoRelatorio
{
    private static readonly ILogger<ServicoRelatorio> logger =
        LoggerFactory.Create(b => b.AddConsole()).CreateLogger<ServicoRelatorio>();

    public string GerarResumoAvaliacoes(List<Turma> turmas, string usuarioLogado)
    {
        logger.LogInformation("Relatório de Resumo de Avaliações gerado pelo usuário: {Usuario}", usuarioLogado);
        StringBuilder sb = new StringBuilder();
        sb.Append("--- Relatório de Resumo de Avaliações ---\n");

        if (turmas == null || turmas.Count == 0)
        {
            sb.Append("Nenhuma turma registrada.\n");
            return sb.ToString();
        }

        foreach (Turma t in turmas)
        {
            sb.Append("Turma: ").Append(t.Codigo).Append(" - ").Append(t.Titulo).Append("\n");

            List<Avaliacao> avaliacoes = t.GetAvaliacoes();
            if (avaliacoes.Count == 0)
            {
                sb.Append("  Sem avaliações registradas.\n");
            }
            else
            {
                foreach (Avaliacao a in avaliacoes)
                {
                    sb.Append("  Avaliação: ")
                      .Append(a.GetType().Name)
                      .Append(" | Valor: ").Append(a.Valor)
                      .Append(" | Peso: ").Append(a.Peso)
                      .Append("\n");
                }
            }
        }
        return sb.ToString();
    }

    public string GerarRelatorioPesos(List<Turma> turmas, string usuarioLogado)
    {
        logger.LogInformation("Relatório de Pesos das Avaliações gerado pelo usuário: {Usuario}", usuarioLogado);
        StringBuilder sb = new StringBuilder();
        sb.Append("--- Relatório de Pesos das Avaliações ---\n");

        if (turmas == null || turmas.Count == 0)
        {
            sb.Append("Nenhuma turma registrada.\n");
            return sb.ToString();
        }

        foreach (Turma t in turmas)
        {
            double pesoTotal = 0.0;
            foreach (Avaliacao a in t.GetAvaliacoes())
            {
                pesoTotal += a.Peso;
            }

            sb.Append("Turma: ").Append(t.Codigo).Append(" - ").Append(t.Titulo).Append("\n");
            // Equivalente ao String.format(Locale.US, "%.1f", pesoTotal)
            sb.Append("  Peso Total: ").Append(pesoTotal.ToString("F1", CultureInfo.InvariantCulture)).Append("\n");

            if (Math.Abs(pesoTotal - 1.0) < 0.0001)
            {
                sb.Append("  Status: Composição VÁLIDA\n");
            }
            else
            {
                sb.Append("  Status: Composição INVÁLIDA\n");
            }
        }
        return sb.ToString();
    }

    public string GerarRelatorioPersistencia(string tipoAtual, string usuarioLogado)
    {
        if (!"ADMIN".Equals(usuarioLogado))
        {
            logger.LogWarning("Falha de autorização: Tentativa de gerar relatório de persistência por {Usuario}", usuarioLogado);
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem gerar relatório de persistência.");
        }
        logger.LogInformation("Relatório de Persistência gerado pelo administrador.");
        return "--- Relatório de Configuração de Persistência ---\nTipo de Persistência Atual: " + tipoAtual + "\n";
    }
}
