using Xunit;
using SistemaAcademico.Models;

namespace SistemaAcademico.Tests.Models;

// Equivalente à classe Java: TurmaIgualdadeTeste
public class TurmaIgualdadeTeste
{
    [Fact]
    public void TurmasComMesmoCodigoDevemSerIguais()
    {
        Turma t1 = new Turma("POO", "Programação Orientada a Objetos");
        Turma t2 = new Turma("POO", "Outro Título");

        Assert.Equal(t1, t2);
        Assert.Equal(t1.GetHashCode(), t2.GetHashCode());
    }

    [Fact]
    public void TurmasComCodigosDiferentesDevemSerDiferentes()
    {
        Turma t1 = new Turma("POO1", "Programação Orientada a Objetos");
        Turma t2 = new Turma("POO2", "Programação Orientada a Objetos");

        Assert.NotEqual(t1, t2);
    }
}
