using System.ComponentModel.DataAnnotations;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Models;

// Equivalente à classe Java: Turma
// Lombok @Getter → propriedades C# com get; privado
// Lombok @EqualsAndHashCode(onlyExplicitlyIncluded = true) com @EqualsAndHashCode.Include em codigo
//   → Equals e GetHashCode sobrescritos baseados apenas em Codigo
public class Turma
{
    [Required(ErrorMessage = "O código da turma não pode ser vazio.")]
    [MinLength(1, ErrorMessage = "O código da turma não pode ser vazio.")]
    public string Codigo { get; private set; }

    [Required(ErrorMessage = "O título da turma não pode ser vazio.")]
    [MinLength(1, ErrorMessage = "O título da turma não pode ser vazio.")]
    public string Titulo { get; private set; }

    private readonly List<Avaliacao> avaliacoes;

    public Turma(string codigo, string titulo)
    {
        Codigo = codigo;
        Titulo = titulo;
        avaliacoes = new List<Avaliacao>();
    }

    // AC6: Dados inválidos de avaliação – rejeitar nulo com IllegalArgumentException
    public void AdicionarAvaliacao(Avaliacao avaliacao)
    {
        if (avaliacao == null)
        {
            throw new ArgumentException("A avaliação não pode ser nula");
        }
        avaliacoes.Add(avaliacao);
    }

    // Retorna cópia defensiva da lista (equivalente ao new ArrayList<>(avaliacoes) Java)
    public List<Avaliacao> GetAvaliacoes()
    {
        return new List<Avaliacao>(avaliacoes);
    }

    // Equivalente a Lombok @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    // Igualdade baseada apenas no campo Codigo
    public override bool Equals(object? obj)
    {
        if (obj is Turma other)
        {
            return string.Equals(Codigo, other.Codigo, StringComparison.Ordinal);
        }
        return false;
    }

    public override int GetHashCode()
    {
        return Codigo?.GetHashCode() ?? 0;
    }
}
