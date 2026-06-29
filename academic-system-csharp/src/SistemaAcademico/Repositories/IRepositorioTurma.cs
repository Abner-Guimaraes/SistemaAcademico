using SistemaAcademico.Models;

namespace SistemaAcademico.Repositories;

// Equivalente à interface Java: RepositorioTurma
// Padrão de projeto: Repository (preservado integralmente)
public interface IRepositorioTurma
{
    void SalvarTodas(List<Turma> turmas);
}
