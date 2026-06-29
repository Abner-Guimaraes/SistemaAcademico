namespace SistemaAcademico.Models;

// Equivalente à classe Java: TrabalhoPratico extends Avaliacao
public class TrabalhoPratico : Avaliacao
{
    public TrabalhoPratico(string nome, double valor, double peso)
        : base(nome, valor, peso)
    {
    }
}
