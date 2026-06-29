namespace SistemaAcademico.Models;

// Equivalente à classe Java: Seminario extends Avaliacao
public class Seminario : Avaliacao
{
    public Seminario(string nome, double valor, double peso)
        : base(nome, valor, peso)
    {
    }
}
