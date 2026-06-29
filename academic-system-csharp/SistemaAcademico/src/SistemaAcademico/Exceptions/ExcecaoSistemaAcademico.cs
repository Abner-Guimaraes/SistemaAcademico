namespace SistemaAcademico.Exceptions;

// Equivalente à classe Java: ExcecaoSistemaAcademico extends RuntimeException
// Em C#, RuntimeException → Exception (todas as exceções são unchecked por padrão)
public class ExcecaoSistemaAcademico : Exception
{
    public ExcecaoSistemaAcademico(string mensagem)
        : base(mensagem)
    {
    }
}
