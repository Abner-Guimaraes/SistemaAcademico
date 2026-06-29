namespace SistemaAcademico.Models;

// Equivalente à classe Java: Usuario
// Lombok @Getter → propriedades C# com get
// Lombok @AllArgsConstructor → construtor gerado com todos os campos
// Lombok @EqualsAndHashCode(onlyExplicitlyIncluded = true) com @EqualsAndHashCode.Include em username
//   → Equals e GetHashCode baseados apenas em Username
public class Usuario
{
    public string Username { get; }
    public string Password { get; }
    public string Role { get; }

    public Usuario(string username, string password, string role)
    {
        Username = username;
        Password = password;
        Role = role;
    }

    // Equivalente a Lombok @EqualsAndHashCode(onlyExplicitlyIncluded = true) com Username incluso
    public override bool Equals(object? obj)
    {
        if (obj is Usuario other)
        {
            return string.Equals(Username, other.Username, StringComparison.Ordinal);
        }
        return false;
    }

    public override int GetHashCode()
    {
        return Username?.GetHashCode() ?? 0;
    }
}
