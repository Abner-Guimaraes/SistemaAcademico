using Xunit;
using SistemaAcademico.Models;
using SistemaAcademico.Services;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Tests.Services;

// Equivalente à classe Java: ServicoPersistenciaTeste
public class ServicoPersistenciaTeste
{
    [Fact]
    // US-2372: AC1, AC4 - Deve alterar persistencia apenas se for ADMIN
    public void TestConfigurarPersistencia()
    {
        ServicoPersistencia servico = new ServicoPersistencia();

        // ADMIN sucesso
        servico.ConfigurarPersistencia("XML", "ADMIN");
        Assert.Equal("XML", servico.GetTipoPersistencia());

        // PROFESSOR falha
        Assert.Throws<ExcecaoAutorizacao>(() =>
        {
            servico.ConfigurarPersistencia("JSON", "PROFESSOR");
        });
    }

    [Fact]
    // US-2373, US-2374: AC5 - O salvamento deve utilizar a persistência configurada
    public void TestSalvarDados()
    {
        ServicoPersistencia servico = new ServicoPersistencia();
        servico.ConfigurarPersistencia("JSON", "ADMIN");

        List<Turma> turmas = new List<Turma>();
        Turma t = new Turma("CC3A", "POO");
        t.AdicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        turmas.Add(t);

        // Não deve lançar exceção — equivalente ao assertDoesNotThrow
        var exception = Record.Exception(() =>
        {
            servico.SalvarDados(turmas, "ADMIN");
        });
        Assert.Null(exception);
    }
}
