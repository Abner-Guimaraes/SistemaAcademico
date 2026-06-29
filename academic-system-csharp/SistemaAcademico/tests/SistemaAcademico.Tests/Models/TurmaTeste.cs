using Xunit;
using SistemaAcademico.Models;

namespace SistemaAcademico.Tests.Models;

// Equivalente à classe Java: TurmaTeste
public class TurmaTeste
{
    [Fact]
    // TUS-2384: Deve considerar turmas iguais se possuirem o mesmo código
    public void TestIgualdadePorCodigo()
    {
        Turma turma1 = new Turma("CC3A", "Orientação a Objetos");
        Turma turma2 = new Turma("CC3A", "Algoritmos e Estruturas de Dados");

        Assert.Equal(turma1, turma2); // "Turmas com o mesmo código devem ser iguais"
        Assert.Equal(turma1.GetHashCode(), turma2.GetHashCode()); // "Turmas com o mesmo código devem ter o mesmo hashCode"
    }

    [Fact]
    // TUS-2384: Deve considerar turmas diferentes se possuirem códigos diferentes
    public void TestDesigualdadePorCodigo()
    {
        Turma turma1 = new Turma("CC3A", "Orientação a Objetos");
        Turma turma2 = new Turma("CC3B", "Orientação a Objetos");

        Assert.NotEqual(turma1, turma2); // "Turmas com códigos diferentes devem ser diferentes"
    }
}
