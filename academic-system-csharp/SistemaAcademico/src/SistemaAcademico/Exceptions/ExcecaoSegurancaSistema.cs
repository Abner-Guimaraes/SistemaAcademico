namespace SistemaAcademico.Exceptions;

// Equivalente à classe Java: ExcecaoSegurancaSistema extends RuntimeException
public class ExcecaoSegurancaSistema : Exception
{
    public ExcecaoSegurancaSistema(string message)
        : base(message)
    {
    }
}
