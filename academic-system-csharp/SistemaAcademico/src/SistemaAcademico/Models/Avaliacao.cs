using System.ComponentModel.DataAnnotations;

namespace SistemaAcademico.Models;

// Equivalente à classe abstrata Java: Avaliacao
// Lombok @Getter → propriedades C# com get; privado
// Jakarta @NotBlank → [Required], [MinLength(1)]
// Jakarta @PositiveOrZero → [Range(0, double.MaxValue)]
public abstract class Avaliacao
{
    [Required(ErrorMessage = "O nome da avaliação não pode ser vazio.")]
    [MinLength(1, ErrorMessage = "O nome da avaliação não pode ser vazio.")]
    public string Nome { get; private set; }

    [Range(0, double.MaxValue, ErrorMessage = "Dados inválidos: valor não pode ser negativo.")]
    public double Valor { get; private set; }

    [Range(0, double.MaxValue, ErrorMessage = "Dados inválidos: peso não pode ser negativo.")]
    public double Peso { get; private set; }

    protected Avaliacao(string nome, double valor, double peso)
    {
        Nome = nome;
        Valor = valor;
        Peso = peso;
    }
}
