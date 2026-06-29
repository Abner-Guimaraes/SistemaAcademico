using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using SistemaAcademico.Validation;

namespace SistemaAcademico.Services;

// Equivalente à classe Java: ServicoTurma
public class ServicoTurma
{
    private readonly List<Turma> turmasCadastradas = new List<Turma>();

    public void RegistrarTurma(string codigo, string titulo, string usuarioAdmin)
    {
        if (!"ADMIN".Equals(usuarioAdmin))
        {
            throw new ExcecaoAutorizacao("Operação negada: Apenas administradores podem registrar turmas.");
        }
        Turma novaTurma = new Turma(codigo, titulo);
        ValidadorDominio.Validate(novaTurma);
        turmasCadastradas.Add(novaTurma);
    }

    public List<Turma> ListarTurmas()
    {
        return turmasCadastradas;
    }
}
