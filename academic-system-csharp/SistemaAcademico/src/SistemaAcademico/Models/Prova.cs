namespace SistemaAcademico.Models;

// Equivalente à classe Java: Prova extends Avaliacao
public class Prova : Avaliacao
{
    public Prova(string nome, double valor, double peso)
        : base(nome, valor, peso)
    {
    }
}
