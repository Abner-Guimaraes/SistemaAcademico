using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Repositories;

// Equivalente à classe Java: RepositorioTurmaJson implements RepositorioTurma
// A geração de JSON é manual (string building), idêntica à implementação Java original.
// Não usamos System.Text.Json para manter fidelidade ao formato e estrutura originais.
public class RepositorioTurmaJson : IRepositorioTurma
{
    private string arquivo = "turmas.json";

    public RepositorioTurmaJson() { }

    public RepositorioTurmaJson(string arquivo)
    {
        this.arquivo = arquivo;
    }

    public void SalvarTodas(List<Turma> turmas)
    {
        try
        {
            using StreamWriter writer = new StreamWriter(arquivo);
            writer.WriteLine("{");
            writer.WriteLine("  \"turmas\": [");
            for (int i = 0; i < turmas.Count; i++)
            {
                Turma turma = turmas[i];
                writer.WriteLine("    {");
                writer.WriteLine("      \"codigo\": \"" + turma.Codigo + "\",");
                writer.WriteLine("      \"titulo\": \"" + turma.Titulo + "\",");
                writer.WriteLine("      \"avaliacoes\": [");
                List<Avaliacao> avaliacoes = turma.GetAvaliacoes();
                for (int j = 0; j < avaliacoes.Count; j++)
                {
                    Avaliacao avaliacao = avaliacoes[j];
                    writer.WriteLine("        {");
                    writer.WriteLine("          \"tipo\": \"" + avaliacao.GetType().Name + "\",");
                    writer.WriteLine("          \"valor\": " + avaliacao.Valor + ",");
                    writer.WriteLine("          \"peso\": " + avaliacao.Peso);
                    if (j < avaliacoes.Count - 1) writer.WriteLine("        },");
                    else writer.WriteLine("        }");
                }
                writer.WriteLine("      ]");
                if (i < turmas.Count - 1) writer.WriteLine("    },");
                else writer.WriteLine("    }");
            }
            writer.WriteLine("  ]");
            writer.WriteLine("}");
        }
        catch (IOException e)
        {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo JSON: " + e.Message);
        }
    }
}
