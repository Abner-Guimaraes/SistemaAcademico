using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Repositories;

// Equivalente à classe Java: RepositorioTurmaTxt implements RepositorioTurma
// java.io.FileWriter / PrintWriter → System.IO.StreamWriter
// writer.printf → string.Format / ToString com InvariantCulture
// Padrão de projeto: Repository + Strategy (preservados)
public class RepositorioTurmaTxt : IRepositorioTurma
{
    private string arquivo = "turmas.txt";

    public RepositorioTurmaTxt() { }

    public RepositorioTurmaTxt(string arquivo)
    {
        this.arquivo = arquivo;
    }

    public void SalvarTodas(List<Turma> turmas)
    {
        try
        {
            using StreamWriter writer = new StreamWriter(arquivo);
            foreach (Turma turma in turmas)
            {
                // AC4: O arquivo TXT deve conter: código, título, tipo, valor, peso
                List<Avaliacao> avaliacoes = turma.GetAvaliacoes();
                if (avaliacoes.Count == 0)
                {
                    writer.WriteLine($"{turma.Codigo}|{turma.Titulo}|SemAvaliacao|0.0|0.0");
                }
                else
                {
                    foreach (Avaliacao avaliacao in avaliacoes)
                    {
                        // Equivalente ao writer.printf("%s|%s|%s|%.2f|%.2f%n", ...)
                        writer.WriteLine(string.Format(
                            System.Globalization.CultureInfo.InvariantCulture,
                            "{0}|{1}|{2}|{3:F2}|{4:F2}",
                            turma.Codigo,
                            turma.Titulo,
                            avaliacao.GetType().Name,
                            avaliacao.Valor,
                            avaliacao.Peso
                        ));
                    }
                }
            }
        }
        catch (IOException e)
        {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo TXT: " + e.Message);
        }
    }
}
