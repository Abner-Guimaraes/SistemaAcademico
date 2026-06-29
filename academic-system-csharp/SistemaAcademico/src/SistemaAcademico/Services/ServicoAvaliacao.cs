using SistemaAcademico.Models;
using SistemaAcademico.Exceptions;
using SistemaAcademico.Validation;

namespace SistemaAcademico.Services;

// Equivalente à classe Java: ServicoAvaliacao
// Injeção de dependência via construtor (Dependency Injection preservado)
public class ServicoAvaliacao
{
    private readonly ServicoTurma servicoTurma;

    public ServicoAvaliacao(ServicoTurma servicoTurma)
    {
        this.servicoTurma = servicoTurma;
    }

    public void RegistrarAvaliacao(string codigoTurma, string nome, string tipo, double valor, double peso, string usuarioLogado)
    {
        if (!"PROFESSOR".Equals(usuarioLogado))
        {
            throw new ExcecaoAutorizacao("Operação negada: Apenas professores podem registrar avaliações.");
        }

        Turma? turmaEncontrada = null;
        foreach (Turma t in servicoTurma.ListarTurmas())
        {
            if (t.Codigo.Equals(codigoTurma))
            {
                turmaEncontrada = t;
                break;
            }
        }

        if (turmaEncontrada == null)
        {
            throw new ExcecaoSistemaAcademico("Turma não encontrada: " + codigoTurma);
        }

        Avaliacao novaAvaliacao = AvaliacaoFactory.Criar(tipo, nome, valor, peso);
        ValidadorDominio.Validate(novaAvaliacao);

        turmaEncontrada.AdicionarAvaliacao(novaAvaliacao);
    }
}
