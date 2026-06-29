using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Repositories;

// Equivalente à classe Java: RepositorioTurmaXml implements RepositorioTurma
// A geração de XML é manual (string building), idêntica à implementação Java original.
// Não usamos XmlSerializer para manter a fidelidade ao formato de saída original.
public class RepositorioTurmaXml : IRepositorioTurma
{
    private string arquivo = "turmas.xml";

    public RepositorioTurmaXml() { }

    public RepositorioTurmaXml(string arquivo)
    {
        this.arquivo = arquivo;
    }

    public void SalvarTodas(List<Turma> turmas)
    {
        try
        {
            using StreamWriter writer = new StreamWriter(arquivo);
            writer.WriteLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.WriteLine("<sistemaAcademico>");
            writer.WriteLine("  <turmas>");
            foreach (Turma turma in turmas)
            {
                writer.WriteLine("    <turma>");
                writer.WriteLine("      <codigo>" + turma.Codigo + "</codigo>");
                writer.WriteLine("      <titulo>" + turma.Titulo + "</titulo>");
                writer.WriteLine("      <avaliacoes>");
                foreach (Avaliacao avaliacao in turma.GetAvaliacoes())
                {
                    writer.WriteLine("        <avaliacao>");
                    writer.WriteLine("          <tipo>" + avaliacao.GetType().Name + "</tipo>");
                    writer.WriteLine("          <valor>" + avaliacao.Valor + "</valor>");
                    writer.WriteLine("          <peso>" + avaliacao.Peso + "</peso>");
                    writer.WriteLine("        </avaliacao>");
                }
                writer.WriteLine("      </avaliacoes>");
                writer.WriteLine("    </turma>");
            }
            writer.WriteLine("  </turmas>");
            writer.WriteLine("</sistemaAcademico>");
        }
        catch (IOException e)
        {
            throw new ExcecaoSistemaAcademico("Erro ao salvar dados no arquivo XML: " + e.Message);
        }
    }
}
