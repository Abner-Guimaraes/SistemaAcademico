using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Models;

// Equivalente à classe Java: GerenciadorDeTurmas
// Classe de domínio utilizada nos testes (RegistroDeAvaliacaoTeste)
public class GerenciadorDeTurmas
{
    // Lista que simula o banco de dados do sistema
    private readonly List<Turma> turmas = new List<Turma>();

    // Método para salvar as turmas que existem na faculdade
    public void SalvarTurma(Turma turma)
    {
        turmas.Add(turma);
    }

    // Método principal que gerencia o registro das avaliações (Cobre AC4 e AC8)
    public void RegistrarAvaliacao(string codigoTurma, Avaliacao avaliacao, string usuarioLogado)
    {
        // AC8: Verifica a autorização imediatamente
        if (!"PROFESSOR".Equals(usuarioLogado))
        {
            throw new ExcecaoAutorizacao("Operação negada: Apenas professores podem registrar avaliações.");
        }

        // Se for professor, procura a turma pelo código (AC4)
        foreach (Turma turma in turmas)
        {
            if (turma.Codigo.Equals(codigoTurma))
            {
                turma.AdicionarAvaliacao(avaliacao); // Registra na turma
                return; // Operação concluída com sucesso
            }
        }

        // Se rodou a lista toda e não encontrou, lança o erro de turma inexistente
        throw new ExcecaoSistemaAcademico("Turma inexistente: " + codigoTurma);
    }
}
