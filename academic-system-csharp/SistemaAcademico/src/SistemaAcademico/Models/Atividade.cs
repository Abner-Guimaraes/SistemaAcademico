namespace SistemaAcademico.Models;

// Equivalente à classe Java: Atividade extends Avaliacao
public class Atividade : Avaliacao
{
    public Atividade(string nome, double valor, double peso)
        : base(nome, valor, peso)
    {
    }
}
