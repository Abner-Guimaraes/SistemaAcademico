using SistemaAcademico.Models;

namespace SistemaAcademico.Repositories;

// Equivalente à classe Java: RepositorioUsuarioTxt implements RepositorioUsuario
// java.io.BufferedReader / FileReader → System.IO.StreamReader
// Optional<Usuario> → Usuario? (nullable reference)
// Comportamento idêntico: ler CSV linha a linha, split por vírgula, retornar usuário ou null
public class RepositorioUsuarioTxt : IRepositorioUsuario
{
    private readonly string caminhoArquivo;

    public RepositorioUsuarioTxt(string caminhoArquivo)
    {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Usuario? BuscarPorUsername(string username)
    {
        try
        {
            using StreamReader reader = new StreamReader(caminhoArquivo);
            string? linha;
            while ((linha = reader.ReadLine()) != null)
            {
                string[] partes = linha.Split(',');
                if (partes.Length >= 3 && partes[0].Equals(username))
                {
                    return new Usuario(partes[0], partes[1], partes[2]);
                }
            }
        }
        catch (Exception)
        {
            // Silencioso, retorna null (equivalente ao Optional.empty())
        }
        return null;
    }
}
