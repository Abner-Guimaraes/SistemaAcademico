using Xunit;
using Moq;
using SistemaAcademico.Models;
using SistemaAcademico.Repositories;
using SistemaAcademico.Services;
using SistemaAcademico.Exceptions;

namespace SistemaAcademico.Tests.Services;

// Equivalente à classe Java: ServicoSegurancaTeste
// @BeforeEach setup() → construtor (xUnit instancia a classe a cada teste)
// Mockito lambda mock (RepositorioUsuario como lambda) → Moq Mock<IRepositorioUsuario>
// O comportamento do mock é idêntico: admin/prof123 retorna usuário, outros retornam null
public class ServicoSegurancaTeste
{
    private readonly ServicoSeguranca servicoSeguranca;

    // Equivalente ao @BeforeEach setup() Java
    // Em xUnit, o construtor executa antes de cada teste
    public ServicoSegurancaTeste()
    {
        // Equivalente ao mock lambda Java:
        // RepositorioUsuario mockRepo = username -> { ... };
        var mockRepo = new Mock<IRepositorioUsuario>();
        mockRepo.Setup(r => r.BuscarPorUsername("admin"))
                .Returns(new Usuario("admin", "admin123", "ADMIN"));
        mockRepo.Setup(r => r.BuscarPorUsername("prof"))
                .Returns(new Usuario("prof", "prof123", "PROFESSOR"));
        mockRepo.Setup(r => r.BuscarPorUsername(It.Is<string>(u => u != "admin" && u != "prof")))
                .Returns((Usuario?)null);

        servicoSeguranca = new ServicoSeguranca(mockRepo.Object);
    }

    [Fact]
    public void DeveAutenticarUsuarioValido()
    {
        Usuario user = servicoSeguranca.Autenticar("admin", "admin123");
        Assert.NotNull(user);
        Assert.Equal("ADMIN", user.Role);
    }

    [Fact]
    public void DeveLancarExcecaoSenhaInvalida()
    {
        Assert.Throws<ExcecaoAutenticacao>(() =>
        {
            servicoSeguranca.Autenticar("admin", "senhaerrada");
        });
    }

    [Fact]
    public void DeveLancarExcecaoUsuarioInexistente()
    {
        Assert.Throws<ExcecaoAutenticacao>(() =>
        {
            servicoSeguranca.Autenticar("desconhecido", "123");
        });
    }

    [Fact]
    public void DeveAutorizarAdmin()
    {
        // Não deve lançar exceção
        var exception = Record.Exception(() => servicoSeguranca.AutorizarAdmin("ADMIN"));
        Assert.Null(exception);
    }

    [Fact]
    public void DeveNegarAutorizacaoProfessor()
    {
        Assert.Throws<ExcecaoAutorizacao>(() =>
        {
            servicoSeguranca.AutorizarAdmin("PROFESSOR");
        });
    }
}
