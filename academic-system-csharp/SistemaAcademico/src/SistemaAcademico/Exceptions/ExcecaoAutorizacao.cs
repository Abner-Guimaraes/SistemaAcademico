namespace SistemaAcademico.Exceptions;

// Equivalente à classe Java: ExcecaoAutorizacao extends ExcecaoSegurancaSistema
public class ExcecaoAutorizacao : ExcecaoSegurancaSistema
{
    public ExcecaoAutorizacao(string message)
        : base(message)
    {
    }
}
