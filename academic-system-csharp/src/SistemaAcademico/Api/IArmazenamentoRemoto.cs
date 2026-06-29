using SistemaAcademico.Models;

namespace SistemaAcademico.Api;

// Equivalente à interface Java: ArmazenamentoRemoto
public interface IArmazenamentoRemoto
{
    bool Autenticar(string token);
    void EnviarDados(List<Turma> turmas);
    List<Turma> RecuperarDados();
}
