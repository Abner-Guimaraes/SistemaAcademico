namespace SistemaAcademico.Exceptions;

// Equivalente à classe Java: ExcecaoEntradaTeclado extends RuntimeException
public class ExcecaoEntradaTeclado : Exception
{
    public ExcecaoEntradaTeclado(string mensagem)
        : base(mensagem)
    {
    }
}
