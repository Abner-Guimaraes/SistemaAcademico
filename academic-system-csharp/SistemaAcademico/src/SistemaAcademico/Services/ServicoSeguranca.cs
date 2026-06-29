using SistemaAcademico.Models;
using SistemaAcademico.Repositories;
using SistemaAcademico.Exceptions;
using Microsoft.Extensions.Logging;

namespace SistemaAcademico.Services;

// Equivalente à classe Java: ServicoSeguranca
// java.util.Optional<Usuario> → Usuario? (nullable)
// Injeção de dependência via construtor (preservada)
public class ServicoSeguranca
{
    private static readonly ILogger<ServicoSeguranca> logger =
        LoggerFactory.Create(b => b.AddConsole()).CreateLogger<ServicoSeguranca>();

    private readonly IRepositorioUsuario repositorio;

    public ServicoSeguranca(IRepositorioUsuario repositorio)
    {
        this.repositorio = repositorio;
    }

    public Usuario Autenticar(string username, string password)
    {
        Usuario? usuario = repositorio.BuscarPorUsername(username);
        if (usuario == null || !usuario.Password.Equals(password))
        {
            logger.LogWarning("Falha de autenticação para o usuário: {Username}", username);
            throw new ExcecaoAutenticacao("Credenciais inválidas.");
        }
        logger.LogInformation("Usuário autenticado com sucesso: {Username}", username);
        return usuario;
    }

    public void AutorizarAdmin(string role)
    {
        if (!"ADMIN".Equals(role))
        {
            logger.LogWarning("Tentativa de acesso negada para o papel: {Role}", role);
            throw new ExcecaoAutorizacao("Acesso negado: Requer privilégios de administrador.");
        }
        logger.LogInformation("Autorização de administrador concedida.");
    }

    public void Logout(string username)
    {
        logger.LogInformation("Usuário efetuou logout com sucesso: {Username}", username);
    }
}
