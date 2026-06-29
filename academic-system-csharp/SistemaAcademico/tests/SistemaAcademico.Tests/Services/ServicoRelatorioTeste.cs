using Xunit;
using SistemaAcademico.Models;
using SistemaAcademico.Services;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Tests.Services;

// Equivalente à classe Java: ServicoRelatorioTeste
public class ServicoRelatorioTeste
{
    [Fact]
    // AC4 - Deve gerar relatório sem erros quando não há turmas registradas
    public void DeveGerarRelatorioSemTurmas()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        string relatorio = servico.GerarResumoAvaliacoes(turmas, "ADMIN");
        Assert.NotNull(relatorio);
        Assert.Contains("Nenhuma turma registrada", relatorio);
    }

    [Fact]
    // AC1, AC3 - Deve listar turma mesmo sem avaliações
    public void DeveListarTurmaSemAvaliacao()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        turmas.Add(new Turma("CC3A", "POO"));

        string relatorio = servico.GerarResumoAvaliacoes(turmas, "ADMIN");
        Assert.Contains("CC3A", relatorio);
        Assert.Contains("POO", relatorio);
        Assert.Contains("Sem avaliações registradas.", relatorio);
    }

    [Fact]
    // AC2 - Deve exibir detalhes da avaliação quando a turma possuir
    public void DeveExibirDetalhesDaAvaliacao()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        Turma t = new Turma("CC3A", "POO");
        t.AdicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        turmas.Add(t);

        string relatorio = servico.GerarResumoAvaliacoes(turmas, "ADMIN");
        Assert.Contains("CC3A", relatorio);
        Assert.Contains("POO", relatorio);
        Assert.Contains("Prova", relatorio);
        Assert.True(relatorio.Contains("10") || relatorio.Contains("10,0"));
        Assert.True(relatorio.Contains("0.5") || relatorio.Contains("0,5"));
    }

    [Fact]
    // AC1, AC2, AC3 - Deve calcular peso total e indicar composição VÁLIDA (peso == 1.0)
    public void DeveGerarRelatorioPesoValido()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        Turma t = new Turma("CC3A", "POO");
        t.AdicionarAvaliacao(new Prova("P1", 10.0, 0.5));
        t.AdicionarAvaliacao(new Prova("P2", 10.0, 0.5));
        turmas.Add(t);

        string relatorio = servico.GerarRelatorioPesos(turmas, "ADMIN");
        Assert.Contains("CC3A", relatorio);
        Assert.True(relatorio.Contains("1.0") || relatorio.Contains("1,0"));
        Assert.Contains("VÁLIDA", relatorio);
        Assert.DoesNotContain("INVÁLIDA", relatorio);
    }

    [Fact]
    // AC4 - Deve indicar composição INVÁLIDA (peso != 1.0)
    public void DeveGerarRelatorioPesoInvalido()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        Turma t = new Turma("CC3A", "POO");
        t.AdicionarAvaliacao(new Prova("P1", 10.0, 0.6));
        turmas.Add(t);

        string relatorio = servico.GerarRelatorioPesos(turmas, "ADMIN");
        Assert.Contains("CC3A", relatorio);
        Assert.True(relatorio.Contains("0.6") || relatorio.Contains("0,6"));
        Assert.Contains("INVÁLIDA", relatorio);
    }

    [Fact]
    // AC5 - Deve exibir peso 0.0 para turma sem avaliações
    public void DeveGerarRelatorioPesoZeroParaTurmaSemAvaliacao()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        List<Turma> turmas = new List<Turma>();
        turmas.Add(new Turma("CC3A", "POO"));

        string relatorio = servico.GerarRelatorioPesos(turmas, "ADMIN");
        Assert.Contains("CC3A", relatorio);
        Assert.True(relatorio.Contains("0.0") || relatorio.Contains("0,0"));
        Assert.Contains("INVÁLIDA", relatorio);
    }

    [Fact]
    // US-2377: AC1, AC4 - Deve gerar relatório de persistência se ADMIN
    public void DeveGerarRelatorioPersistencia()
    {
        ServicoRelatorio servico = new ServicoRelatorio();
        string relatorio = servico.GerarRelatorioPersistencia("JSON", "ADMIN");
        Assert.Contains("JSON", relatorio);

        Assert.Throws<ExcecaoAutorizacao>(() =>
        {
            servico.GerarRelatorioPersistencia("JSON", "PROFESSOR");
        });
    }
}
