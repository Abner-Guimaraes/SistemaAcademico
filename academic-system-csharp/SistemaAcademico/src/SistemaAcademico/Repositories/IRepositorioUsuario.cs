using SistemaAcademico.Models;

namespace SistemaAcademico.Repositories;

// Equivalente à interface Java: RepositorioUsuario
// java.util.Optional<Usuario> → em C# representado como Usuario? (nullable reference type)
// O contrato é o mesmo: buscar por username, retornar o usuário ou indicar ausência.
public interface IRepositorioUsuario
{
    Usuario? BuscarPorUsername(string username);
}
