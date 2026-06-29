namespace SistemaAcademico.Exceptions;

// Equivalente à classe Java: ExcecaoAutenticacao extends ExcecaoSegurancaSistema
public class ExcecaoAutenticacao : ExcecaoSegurancaSistema
{
    public ExcecaoAutenticacao(string message)
        : base(message)
    {
    }
}
